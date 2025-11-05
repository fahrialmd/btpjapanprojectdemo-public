package customer.btpjapanprojectdemo.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import customer.btpjapanprojectdemo.model.InvoiceGetResponseDTO;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO;
import customer.btpjapanprojectdemo.service.SupplierInvoiceReplicationService;
import customer.btpjapanprojectdemo.service.impl.SAPCloudODataClient.SAPCommUser;
import customer.btpjapanprojectdemo.util.InvoiceMapper;

@Service
public class SupplierInvoiceReplicationServiceImpl implements SupplierInvoiceReplicationService {

    private final GenericCqnService genericCqnService;
    private final SAPCloudODataClient sapCloudODataClient;
    private final ObjectMapper objectMapper;
    private HashMap<String, InvoiceGetResponseDTO> getResponseList;

    public SupplierInvoiceReplicationServiceImpl(
            GenericCqnService genericCqnService,
            SAPCloudODataClient sapCloudODataClient) {
        this.genericCqnService = genericCqnService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        this.sapCloudODataClient = sapCloudODataClient;
    }

    @Override
    public List<SupplierInvoice> readSupplierInvoice(CdsReadEventContext context) {

        // Fetch All Invoice Replication Log from DB
        List<InvoiceLog> invoiceLogList = genericCqnService.getInvoiceLogPO();

        // Fetch PO mapping that has no Subcontracting Invoice
        List<String> poList = new ArrayList<>();
        for (int i = 0; i < invoiceLogList.size(); i++) {
            InvoiceLog invoiceLog = invoiceLogList.get(i);

            poList.add(invoiceLog.getPOOriginal());
        }

        List<POMapping> poMappingList = genericCqnService.getPoMappings(poList);

        List<String> oriPOList = new ArrayList<>();
        for (int i = 0; i < poMappingList.size(); i++) {
            POMapping poMapping = poMappingList.get(i);

            poList.add(poMapping.getOriginalPo());
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
                InvoiceGetResponseDTO invoiceObject = objectMapper.convertValue(invoiceJson,
                        InvoiceGetResponseDTO.class);

                // get Standard PO Number
                String standardPO = invoiceObject.getTo_SuplrInvcItemPurOrdRef().getResults().getFirst()
                        .getPurchaseOrder();

                // if standardPO is in the List of PO that has no Subcontracting Invoice
                // then save Invoice
                if (oriPOList.contains(standardPO)) {
                    SupplierInvoice supplierInvoice = InvoiceMapper
                            .invoiceGetResponseDTOtoSupplierInvoice(invoiceObject);
                    getResponseList.put(invoiceObject.getSupplierInvoice(), invoiceObject);
                    supplierInvoices.add(supplierInvoice);
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
        InvoiceGetResponseDTO invoiceGetResponseDTO = getResponseList.get(supplierInvoiceNo);

        InvoicePostRequestDTO invoicePostRequestDTO = InvoiceMapper.getResponseToPostRequest(invoiceGetResponseDTO);

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

    private void logInvoiceReplication(InvoiceGetResponseDTO invoiceGetResponseDTO,
            InvoicePostRequestDTO invoicePostRequestDTO, String invoiceNo, String fiscalYear) {
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
                invoiceGetResponseDTO.getTo_SuplrInvcItemPurOrdRef().getResults().getFirst().getPurchaseOrder());

        invoiceLog.setTimestamp(Instant.now());

        genericCqnService.insertInvoiceLog(invoiceLog);
    }

}
