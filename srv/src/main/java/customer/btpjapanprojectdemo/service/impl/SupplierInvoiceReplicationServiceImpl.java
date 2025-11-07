package customer.btpjapanprojectdemo.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.cqn.AnalysisResult;
import com.sap.cds.ql.cqn.CqnAnalyzer;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.services.cds.CdsReadEventContext;

import cds.gen.mainservice.InvoiceLog;
import cds.gen.mainservice.InvoiceLog_;
import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.SupplierInvoice;
import cds.gen.mainservice.SupplierInvoiceReplicateInvoicesContext;
import customer.btpjapanprojectdemo.exception.BusinessException;
import customer.btpjapanprojectdemo.model.InvoiceDTO;
import customer.btpjapanprojectdemo.model.MaterialDocumentItemKeyDTO;
import customer.btpjapanprojectdemo.service.SupplierInvoiceReplicationService;
import customer.btpjapanprojectdemo.service.impl.SAPCloudODataClient.SAPCommUser;
import customer.btpjapanprojectdemo.util.InvoiceMapper;

@Service
public class SupplierInvoiceReplicationServiceImpl implements SupplierInvoiceReplicationService {

    private final GenericCqnService genericCqnService;
    private final SAPCloudODataClient sapCloudODataClient;
    private final ObjectMapper objectMapper;
    private HashMap<String, InvoiceDTO> getResponseMap; // key : Invoice No
    private HashMap<String, HashMap<String, MaterialDocumentItemKeyDTO>> matdocMap; // key : PO num, PO item
    private HashMap<String,String> poRepToOriList;

    public SupplierInvoiceReplicationServiceImpl(
            GenericCqnService genericCqnService,
            SAPCloudODataClient sapCloudODataClient) {
        this.genericCqnService = genericCqnService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        this.sapCloudODataClient = sapCloudODataClient;
        this.getResponseMap = new HashMap<>();
        this.matdocMap = new HashMap<>();
        this.poRepToOriList = new HashMap<>();
    }

    @Override
    public List<SupplierInvoice> readSupplierInvoice(CdsReadEventContext context) {

        // Fetch All Invoice Replication Log from DB
        List<InvoiceLog> invoiceLogList = genericCqnService.getInvoiceLogPO();

        // Fetch PO mapping that has no Subcontracting Invoice
        List<String> loggedPOList = new ArrayList<>();
        for (int i = 0; i < invoiceLogList.size(); i++) {
            InvoiceLog invoiceLog = invoiceLogList.get(i);

            loggedPOList.add(invoiceLog.getPOOriginal());
        }

        List<POMapping> poMappingList = genericCqnService.getPoMappings(loggedPOList);

        // Create Set of Repli PO
        // create Map of PO repli to PO ori 
        Set<String> repliPOSet = new HashSet<>();
        for (int i = 0; i < poMappingList.size(); i++) {
            POMapping poMapping = poMappingList.get(i);

            repliPOSet.add(poMapping.getReplicaPo());
            poRepToOriList.put(poMapping.getReplicaPo(), poMapping.getOriginalPo());
        }

        // Fetch Invoice from Read API
        String readUrl = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("my200132.s4hana.sapcloud.cn")
                .path("/sap/opu/odata/sap/API_SUPPLIERINVOICE_PROCESS_SRV/A_SupplierInvoice")
                .queryParam("$expand",
                        "to_SuplrInvcItemPurOrdRef/to_SupplierInvoiceItmAcctAssgmt,to_SupplierInvoiceTax")
                .build().toUriString();

        JsonNode invoicesNode = sapCloudODataClient.executeRequest(
                HttpMethod.GET,
                readUrl,
                null,
                SAPCommUser.INVOICE_REGISTRATION);

        // Find Invoice with Standard PO that is not in Invoice Replication Log
        List<SupplierInvoice> supplierInvoices = new ArrayList<>();

        if (invoicesNode.isArray()) {
            invoicesNode.forEach(invoiceJson -> {

                // parse response to Invoice Get Response DTO Class
                InvoiceDTO invoiceObject = objectMapper.convertValue(invoiceJson,
                        InvoiceDTO.class);

                // get Standard PO Number
                String standardPO = invoiceObject.getTo_SuplrInvcItemPurOrdRef().getResults().getFirst()
                        .getPurchaseOrder();

                // if standardPO is in the List of PO that has no Subcontracting Invoice
                // then save Invoice
                if (repliPOSet.contains(standardPO)) {
                    SupplierInvoice supplierInvoice = InvoiceMapper
                            .invoiceGetResponseDTOtoSupplierInvoice(invoiceObject);
                    getResponseMap.put(invoiceObject.getSupplierInvoice(), invoiceObject);
                    supplierInvoices.add(supplierInvoice);
                }

            });
        }

        // Create Set of Ori PO
        Set<String> oriPOSet = new HashSet<>();
        for (InvoiceDTO invoice : getResponseMap.values()) {
            String repliPO = invoice.getTo_SuplrInvcItemPurOrdRef().getResults().getFirst()
                        .getPurchaseOrder();
            String oriPO = poRepToOriList.get(repliPO);

            oriPOSet.add(oriPO);
        }

        // Combine Ori Set into a single string for filter
        String poFilter = oriPOSet.stream()
            .map(po -> String.format("(PurchaseOrder eq '%s' )",po))
            .collect(Collectors.joining(" or "));

        // Fetch all matdoc data
        String readMatdocUrl = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("my200132.s4hana.sapcloud.cn")
                .path("/sap/opu/odata/sap/API_MATERIAL_DOCUMENT_SRV/A_MaterialDocumentItem")
                .queryParam("$filter",
                        poFilter)
                .build().toUriString();

        JsonNode matdocNode = sapCloudODataClient.executeRequest(
                HttpMethod.GET,
                readMatdocUrl,
                null,
                SAPCommUser.GOODS_MOVEMENT);
        
        if (matdocNode.isArray()) {
            matdocNode.forEach(matdocJson -> {

                // parse response to MaterialDocumentItemKeyDTO and save it
                MaterialDocumentItemKeyDTO matdocObject = objectMapper.convertValue(matdocJson,
                        MaterialDocumentItemKeyDTO.class);

                if (this.matdocMap.containsKey(matdocObject.getPurchaseOrder())) {
                    if (!this.matdocMap.get(matdocObject.getPurchaseOrder()).containsKey(matdocObject.getPurchaseOrderItem())) {
                        this.matdocMap.get(matdocObject.getPurchaseOrder()).put(matdocObject.getPurchaseOrderItem(), matdocObject);
                        
                    }
                } else {
                    HashMap<String, MaterialDocumentItemKeyDTO> matdocInnerMap = new HashMap<>();
                    matdocInnerMap.put(matdocObject.getPurchaseOrderItem(), matdocObject);
                    this.matdocMap.put(matdocObject.getPurchaseOrder(), matdocInnerMap);
                }
            });
        }


        return supplierInvoices;
    }

    @Override
    public void replicateInvoice(SupplierInvoiceReplicateInvoicesContext context) {
        // get the selected invoice
        CqnAnalyzer cqnAnalyzer = CqnAnalyzer.create(context.getModel());
        AnalysisResult analysisResult = cqnAnalyzer.analyze(context.getCqn().ref());
        String supplierInvoiceNo = (String) analysisResult.targetKeys().get(SupplierInvoice.INVOICE_NO);

        // map invoice response to request
        InvoiceDTO invoiceGetResponseDTO = getResponseMap.get(supplierInvoiceNo);

        InvoiceDTO invoicePostRequestDTO = InvoiceMapper.getResponseToPostRequest(invoiceGetResponseDTO, poRepToOriList, matdocMap);

        String jsonRequest = "";
        try {
            jsonRequest = objectMapper.writeValueAsString(invoicePostRequestDTO);
        } catch (Exception e) {
            throw new BusinessException("Failed to parse request to JSON :" + e);
        }

        // Call POST API
        String postUrl = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("my200132.s4hana.sapcloud.cn")
                .path("/sap/opu/odata/sap/API_SUPPLIERINVOICE_PROCESS_SRV/A_SupplierInvoice")
                .build().toUriString();

        JsonNode createdInvoiceNode = sapCloudODataClient.executeRequest(
                HttpMethod.POST,
                postUrl,
                jsonRequest,
                SAPCommUser.INVOICE_REGISTRATION);

        String invoiceNo = createdInvoiceNode.get("SupplierInvoice").asText();
        String fiscalYear = createdInvoiceNode.get("FiscalYear").asText();

        String successMsg = String.format("Invoice Posted %s/%s", invoiceNo, fiscalYear);

        logInvoiceReplication(invoiceGetResponseDTO, invoicePostRequestDTO, invoiceNo, fiscalYear);

        context.getMessages().success(successMsg);
    }

    private void logInvoiceReplication(InvoiceDTO invoiceGetResponseDTO,
            InvoiceDTO invoicePostRequestDTO, String invoiceNo, String fiscalYear) {
        InvoiceLog invoiceLog = InvoiceLog.create();

        invoiceLog.setInvoiceReplicated(invoiceGetResponseDTO.getSupplierInvoice());
        invoiceLog.setYearReplicated(invoiceGetResponseDTO.getFiscalYear());

        LocalDate postingDateRepli = InvoiceMapper.convertJsonToDate(invoiceGetResponseDTO.getPostingDate());
        invoiceLog.setPostingPeriodReplicated(String.valueOf(postingDateRepli.getMonthValue()));

        invoiceLog.setPOReplicated(
                invoiceGetResponseDTO.getTo_SuplrInvcItemPurOrdRef().getResults().getFirst().getPurchaseOrder());

        invoiceLog.setInvoiceOriginal(invoiceNo);
        invoiceLog.setYearOriginal(fiscalYear);

        LocalDate postingDateOri = InvoiceMapper.convertJsonToDate(invoicePostRequestDTO.getPostingDate());
        invoiceLog.setPostingPeriodOriginal(String.valueOf(postingDateOri.getMonthValue()));

        invoiceLog.setPOOriginal(
                invoicePostRequestDTO.getTo_SuplrInvcItemPurOrdRef().getResults().getFirst().getPurchaseOrder());

        invoiceLog.setTimestamp(Instant.now());

        genericCqnService.insertInvoiceLog(invoiceLog);
    }

}
