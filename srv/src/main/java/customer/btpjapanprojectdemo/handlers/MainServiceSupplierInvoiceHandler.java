package customer.btpjapanprojectdemo.handlers;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sap.cds.ResultBuilder;
import com.sap.cds.ql.cqn.AnalysisResult;
import com.sap.cds.ql.cqn.CqnAnalyzer;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.SupplierInvoice;
import cds.gen.mainservice.SupplierInvoiceReplicateInvoicesContext;
import cds.gen.mainservice.SupplierInvoice_;
import customer.btpjapanprojectdemo.model.InvoiceGetResponseDTO;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.Result;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.ToSuplrInvcItemPurOrdRef;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.ToSupplierInvoiceItmAcctAssgmt;
import customer.btpjapanprojectdemo.model.InvoiceSuplrInvcItemPurOrdRefGetResponseDTO;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceSupplierInvoiceHandler implements EventHandler {

    // Constant
    private static final String INVOICE_READ_URL = "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_SUPPLIERINVOICE_PROCESS_SRV/A_SupplierInvoice?%24inlinecount=allpages&%24top=50";
    private static final String INVOICE_POST_URL = "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_SUPPLIERINVOICE_PROCESS_SRV/A_SupplierInvoice";
    private static final String INVOICE_USERNAME = "INVOICEUSER_49223";
    private static final String INVOICE_PASSWORD = "XdqSnTPjtkJ{rKSPR26K<6#7zpK6#LQg+AVee#bU";

    // Variable for POST API
    private String csrfToken;
    private List<String> cookieList;
    private HashMap<String, InvoiceGetResponseDTO> getResponseList;

    private final RestTemplate restTemplate;

    private PersistenceService db;

    public MainServiceSupplierInvoiceHandler(PersistenceService persistenceService) {
        this.restTemplate = createRestTemplate();
        this.db = persistenceService;
        this.getResponseList = new HashMap<>();
    }

    private RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }

    @On(event = CqnService.EVENT_READ, entity = SupplierInvoice_.CDS_NAME)
    public void readSupplierInvoice(CdsReadEventContext context) {

        ArrayList<SupplierInvoice> supplierInvoices = new ArrayList<>();

        ResponseEntity<String> responseEntity = callReadAPI(INVOICE_READ_URL);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // Save header for CSRF Token
            csrfToken = responseEntity.getHeaders().get("x-csrf-token").get(0);
            cookieList = responseEntity.getHeaders().get("set-cookie");

            // Parse response body
            String response = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode rootNode = objectMapper.readTree(response);
                JsonNode dataNode = rootNode.get("d");

                JsonNode resultNode = dataNode.get("results");
                resultNode.forEach(invoiceJson -> {

                    // mapped response to Invoice Get Response DTO Class
                    InvoiceGetResponseDTO invoiceObject = objectMapper.convertValue(invoiceJson,
                            InvoiceGetResponseDTO.class);
                    getResponseList.put(invoiceObject.getSupplierInvoice(), invoiceObject);

                    // Fill SupplierInvoice for Fiori Frontend
                    SupplierInvoice supplierInvoice = SupplierInvoice.create();

                    supplierInvoice.setInvoiceNo(invoiceObject.getSupplierInvoice());
                    supplierInvoice.setFiscalYear(invoiceObject.getFiscalYear());
                    supplierInvoice.setInvoicingParty(invoiceObject.getInvoicingParty());
                    supplierInvoice.setCompanyCode(invoiceObject.getCompanyCode());

                    BigDecimal grossInvoice = new BigDecimal(invoiceObject.getInvoiceGrossAmount());
                    supplierInvoice.setGrossInvoice(grossInvoice);

                    supplierInvoice.setDocumentCurrencyCode(invoiceObject.getDocumentCurrency());
                    supplierInvoice.setReference(invoiceObject.getSupplierInvoiceIDByInvcgParty());

                    supplierInvoice.setPostingDate(convertJsonToDate(invoiceObject.getPostingDate()));
                    supplierInvoice.setInvoiceDate(convertJsonToDate(invoiceObject.getDocumentDate()));

                    supplierInvoices.add(supplierInvoice);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ResultBuilder resultBuilder = ResultBuilder.selectedRows(supplierInvoices);
        if (context.getCqn().hasInlineCount()) {
            resultBuilder.inlineCount(supplierInvoices.size());
        }
        context.setResult(resultBuilder.result());
    }

    private ResponseEntity<String> callReadAPI(String url) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("DataServiceVersion", "2.0");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(INVOICE_USERNAME, INVOICE_PASSWORD);
        httpHeaders.set("x-csrf-token", "fetch");

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpEntity,
                    String.class);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private LocalDate convertJsonToDate(String jsonEpochDate) {
        String dateEpoch = jsonEpochDate.replaceAll("[^0-9]", "");
        Instant dateinstant = Instant.ofEpochMilli(Long.valueOf(dateEpoch));
        LocalDate date = dateinstant.atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    @On(event = SupplierInvoiceReplicateInvoicesContext.CDS_NAME, entity = SupplierInvoice_.CDS_NAME)
    public void replicateInvoice(SupplierInvoiceReplicateInvoicesContext context) {

        // get the selected invoice
        CqnAnalyzer cqnAnalyzer = CqnAnalyzer.create(context.getModel());
        AnalysisResult analysisResult = cqnAnalyzer.analyze(context.getCqn().ref());
        String supplierInvoiceNo = (String) analysisResult.targetKeys().get(SupplierInvoice.INVOICE_NO);

        // map the invoice to post request body
        InvoiceGetResponseDTO invoiceGetResponseDTO = getResponseList.get(supplierInvoiceNo);

        InvoicePostRequestDTO invoicePostRequestDTO = mapResponseRequest(invoiceGetResponseDTO);

        // print json for debug
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(invoicePostRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // set http headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("DataServiceVersion", "2.0");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(INVOICE_USERNAME, INVOICE_PASSWORD);
        httpHeaders.set("x-csrf-token", csrfToken);
        httpHeaders.set("Cookie", String.join("; ", cookieList));

        HttpEntity<String> httpEntity = new HttpEntity<>(json, httpHeaders);

        // call POST API
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    INVOICE_POST_URL,
                    HttpMethod.POST,
                    httpEntity,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Parse response body
                String response = responseEntity.getBody();

                // ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);

                JsonNode dataNode = rootNode.get("d");

                String invoiceNo = dataNode.get("SupplierInvoice").asText();
                String fiscalYear = dataNode.get("FiscalYear").asText();

                String successMsg = String.format("Invoice Posted %s/%s", invoiceNo, fiscalYear);

                context.getMessages().success(successMsg);
                context.setCompleted();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        context.setCompleted();
    }

    private InvoicePostRequestDTO mapResponseRequest(InvoiceGetResponseDTO invoiceGetResponseDTO) {
        InvoicePostRequestDTO invoicePostRequestDTO = new InvoicePostRequestDTO();
        invoicePostRequestDTO.setCompanyCode(invoiceGetResponseDTO.getCompanyCode());
        invoicePostRequestDTO.setDocumentDate(invoiceGetResponseDTO.getDocumentDate());
        invoicePostRequestDTO.setPostingDate(invoiceGetResponseDTO.getPostingDate());
        invoicePostRequestDTO
                .setSupplierInvoiceIDByInvcgParty(invoiceGetResponseDTO.getSupplierInvoiceIDByInvcgParty());
        invoicePostRequestDTO.setInvoicingParty(invoiceGetResponseDTO.getInvoicingParty());
        invoicePostRequestDTO.setDocumentCurrency(invoiceGetResponseDTO.getDocumentCurrency());
        invoicePostRequestDTO.setInvoiceGrossAmount(invoiceGetResponseDTO.getInvoiceGrossAmount());
        invoicePostRequestDTO.setUnplannedDeliveryCost(invoiceGetResponseDTO.getUnplannedDeliveryCost());
        invoicePostRequestDTO.setDocumentHeaderText(invoiceGetResponseDTO.getDocumentHeaderText());
        invoicePostRequestDTO.setReconciliationAccount(invoiceGetResponseDTO.getReconciliationAccount());
        invoicePostRequestDTO.setManualCashDiscount(invoiceGetResponseDTO.getManualCashDiscount());
        invoicePostRequestDTO.setPaymentTerms(invoiceGetResponseDTO.getPaymentTerms());
        invoicePostRequestDTO.setDueCalculationBaseDate(invoiceGetResponseDTO.getDueCalculationBaseDate());
        invoicePostRequestDTO.setCashDiscount1Percent(invoiceGetResponseDTO.getCashDiscount1Percent());
        invoicePostRequestDTO.setCashDiscount1Days(invoiceGetResponseDTO.getCashDiscount1Days());
        invoicePostRequestDTO.setCashDiscount2Percent(invoiceGetResponseDTO.getCashDiscount2Percent());
        invoicePostRequestDTO.setCashDiscount2Days(invoiceGetResponseDTO.getCashDiscount2Days());
        invoicePostRequestDTO.setNetPaymentDays(invoiceGetResponseDTO.getNetPaymentDays());
        invoicePostRequestDTO.setPaymentBlockingReason(invoiceGetResponseDTO.getPaymentBlockingReason());
        invoicePostRequestDTO.setAccountingDocumentType(invoiceGetResponseDTO.getAccountingDocumentType());
        invoicePostRequestDTO.setBpBankAccountInternalID(invoiceGetResponseDTO.getBPBankAccountInternalID());
        invoicePostRequestDTO.setSupplierInvoiceStatus(invoiceGetResponseDTO.getSupplierInvoiceStatus());
        invoicePostRequestDTO.setIndirectQuotedExchangeRate(invoiceGetResponseDTO.getIndirectQuotedExchangeRate());
        invoicePostRequestDTO.setDirectQuotedExchangeRate(invoiceGetResponseDTO.getDirectQuotedExchangeRate());
        invoicePostRequestDTO
                .setStateCentralBankPaymentReason(invoiceGetResponseDTO.getStateCentralBankPaymentReason());
        invoicePostRequestDTO.setSupplyingCountry(invoiceGetResponseDTO.getSupplyingCountry());
        invoicePostRequestDTO.setPaymentMethod(invoiceGetResponseDTO.getPaymentMethod());
        invoicePostRequestDTO.setPaymentMethodSupplement(invoiceGetResponseDTO.getPaymentMethodSupplement());
        invoicePostRequestDTO.setPaymentReference(invoiceGetResponseDTO.getPaymentReference());
        invoicePostRequestDTO.setInvoiceReference(invoiceGetResponseDTO.getInvoiceReference());
        invoicePostRequestDTO.setInvoiceReferenceFiscalYear(invoiceGetResponseDTO.getInvoiceReferenceFiscalYear());
        invoicePostRequestDTO.setFixedCashDiscount(invoiceGetResponseDTO.getFixedCashDiscount());
        invoicePostRequestDTO.setUnplannedDeliveryCostTaxCode(invoiceGetResponseDTO.getUnplannedDeliveryCostTaxCode());
        invoicePostRequestDTO
                .setUnplndDelivCostTaxJurisdiction(invoiceGetResponseDTO.getUnplndDelivCostTaxJurisdiction());
        invoicePostRequestDTO.setUnplndDeliveryCostTaxCountry(invoiceGetResponseDTO.getUnplndDeliveryCostTaxCountry());
        invoicePostRequestDTO.setAssignmentReference(invoiceGetResponseDTO.getAssignmentReference());
        invoicePostRequestDTO.setSupplierPostingLineItemText(invoiceGetResponseDTO.getSupplierPostingLineItemText());
        invoicePostRequestDTO.setTaxIsCalculatedAutomatically(invoiceGetResponseDTO.isTaxIsCalculatedAutomatically());
        invoicePostRequestDTO.setBusinessPlace(invoiceGetResponseDTO.getBusinessPlace());
        invoicePostRequestDTO.setBusinessSectionCode(invoiceGetResponseDTO.getBusinessSectionCode());
        invoicePostRequestDTO.setBusinessArea(invoiceGetResponseDTO.getBusinessArea());
        invoicePostRequestDTO
                .setSuplrInvcIsCapitalGoodsRelated(invoiceGetResponseDTO.isSuplrInvcIsCapitalGoodsRelated());
        invoicePostRequestDTO.setSupplierInvoiceIsCreditMemo(invoiceGetResponseDTO.getSupplierInvoiceIsCreditMemo());
        invoicePostRequestDTO.setPaytSlipWthRefSubscriber(invoiceGetResponseDTO.getPaytSlipWthRefSubscriber());
        invoicePostRequestDTO.setPaytSlipWthRefCheckDigit(invoiceGetResponseDTO.getPaytSlipWthRefCheckDigit());
        invoicePostRequestDTO.setPaytSlipWthRefReference(invoiceGetResponseDTO.getPaytSlipWthRefReference());
        invoicePostRequestDTO.setTaxDeterminationDate(invoiceGetResponseDTO.getTaxDeterminationDate());
        invoicePostRequestDTO.setTaxReportingDate(invoiceGetResponseDTO.getTaxReportingDate());
        invoicePostRequestDTO.setTaxFulfillmentDate(invoiceGetResponseDTO.getTaxFulfillmentDate());
        invoicePostRequestDTO.setInvoiceReceiptDate(invoiceGetResponseDTO.getInvoiceReceiptDate());
        invoicePostRequestDTO
                .setDeliveryOfGoodsReportingCntry(invoiceGetResponseDTO.getDeliveryOfGoodsReportingCntry());
        invoicePostRequestDTO.setSupplierVATRegistration(invoiceGetResponseDTO.getSupplierVATRegistration());
        invoicePostRequestDTO.setEuTriangularDeal(invoiceGetResponseDTO.isEUTriangularDeal());
        invoicePostRequestDTO
                .setSuplrInvcDebitCrdtCodeDelivery(invoiceGetResponseDTO.getSuplrInvcDebitCrdtCodeDelivery());
        invoicePostRequestDTO
                .setSuplrInvcDebitCrdtCodeReturns(invoiceGetResponseDTO.getSuplrInvcDebitCrdtCodeReturns());
        invoicePostRequestDTO.setRetentionDueDate(invoiceGetResponseDTO.getRetentionDueDate());
        invoicePostRequestDTO.setPaymentReason(invoiceGetResponseDTO.getPaymentReason());
        invoicePostRequestDTO.setHouseBank(invoiceGetResponseDTO.getHouseBank());
        invoicePostRequestDTO.setHouseBankAccount(invoiceGetResponseDTO.getHouseBankAccount());
        invoicePostRequestDTO.setAlternativePayeePayer(invoiceGetResponseDTO.getAlternativePayeePayer());
        invoicePostRequestDTO.setIn_GSTPartner(invoiceGetResponseDTO.getIN_GSTPartner());
        invoicePostRequestDTO.setIn_GSTPlaceOfSupply(invoiceGetResponseDTO.getIN_GSTPlaceOfSupply());
        invoicePostRequestDTO.setIn_InvoiceReferenceNumber(invoiceGetResponseDTO.getIN_InvoiceReferenceNumber());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef1());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate1());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef2());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate2());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef3(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef3());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate3(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate3());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef4(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef4());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate4(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate4());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef5(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef5());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate5(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate5());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificBP1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificBP1());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificBP2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificBP2());

        invoicePostRequestDTO.setTo_BR_SupplierInvoiceNFDocument(null);

        // Get to_SuplrInvcItemPurOrdRef
        ArrayList<Result> results = new ArrayList<>();

        ResponseEntity<String> responseEntity = callReadAPI(invoiceGetResponseDTO.getTo_SuplrInvcItemPurOrdRef().get__deferred().getUri());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // Parse response body
            String response = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode rootNode = objectMapper.readTree(response);
                JsonNode dataNode = rootNode.get("d");

                JsonNode resultNode = dataNode.get("results");
                resultNode.forEach(invoiceJson -> {

                    // mapped response to Invoice PO Ref Get Response DTO Class
                    InvoiceSuplrInvcItemPurOrdRefGetResponseDTO invoicePORefObject = objectMapper.convertValue(invoiceJson,
                            InvoiceSuplrInvcItemPurOrdRefGetResponseDTO.class);

                    // Fill Result for to_SuplrInvcItemPurOrdRef
                    Result result = new Result();

                    result.setSupplierInvoiceItem(invoicePORefObject.getSupplierInvoiceItem());
                    result.setPurchaseOrder(invoicePORefObject.getPurchaseOrder());
                    result.setPurchaseOrderItem(invoicePORefObject.getPurchaseOrderItem());
                    result.setPlant(invoicePORefObject.getPlant());
                    result.setReferenceDocument(invoicePORefObject.getReferenceDocument());
                    result.setReferenceDocumentFiscalYear(invoicePORefObject.getReferenceDocumentFiscalYear());
                    result.setReferenceDocumentItem(invoicePORefObject.getReferenceDocumentItem());
                    result.setIsSubsequentDebitCredit(invoicePORefObject.getIsSubsequentDebitCredit());
                    result.setTaxCode(invoicePORefObject.getTaxCode());
                    result.setTaxJurisdiction(invoicePORefObject.getTaxJurisdiction());
                    result.setDocumentCurrency(invoicePORefObject.getDocumentCurrency());
                    result.setSupplierInvoiceItemAmount(invoicePORefObject.getSupplierInvoiceItemAmount());
                    result.setPurchaseOrderQuantityUnit(invoicePORefObject.getPurchaseOrderQuantityUnit());
                    result.setPurchaseOrderQtyUnitSAPCode(invoicePORefObject.getPurchaseOrderQtyUnitSAPCode());
                    result.setPurchaseOrderQtyUnitISOCode(invoicePORefObject.getPurchaseOrderQtyUnitISOCode());
                    result.setQuantityInPurchaseOrderUnit(invoicePORefObject.getQuantityInPurchaseOrderUnit());
                    result.setPurchaseOrderPriceUnit(invoicePORefObject.getPurchaseOrderPriceUnit());
                    result.setPurchaseOrderPriceUnitSAPCode(invoicePORefObject.getPurchaseOrderPriceUnitSAPCode());
                    result.setPurchaseOrderPriceUnitISOCode(invoicePORefObject.getPurchaseOrderPriceUnitISOCode());
                    result.setQtyInPurchaseOrderPriceUnit(invoicePORefObject.getQtyInPurchaseOrderPriceUnit());
                    result.setSuplrInvcDeliveryCostCndnType(invoicePORefObject.getSuplrInvcDeliveryCostCndnType());
                    result.setSuplrInvcDeliveryCostCndnStep(invoicePORefObject.getSuplrInvcDeliveryCostCndnStep());
                    result.setSuplrInvcDeliveryCostCndnCount(invoicePORefObject.getSuplrInvcDeliveryCostCndnCount());
                    result.setSupplierInvoiceItemText(invoicePORefObject.getSupplierInvoiceItemText());
                    result.setFreightSupplier(invoicePORefObject.getFreightSupplier());
                    result.setNotCashDiscountLiable(invoicePORefObject.isNotCashDiscountLiable());
                    result.setRetentionAmountInDocCurrency(invoicePORefObject.getRetentionAmountInDocCurrency());
                    result.setRetentionPercentage(invoicePORefObject.getRetentionPercentage());
                    result.setRetentionDueDate(invoicePORefObject.getRetentionDueDate());
                    result.setSuplrInvcItmIsNotRlvtForRtntn(invoicePORefObject.isSuplrInvcItmIsNotRlvtForRtntn());
                    result.setServiceEntrySheet(invoicePORefObject.getServiceEntrySheet());
                    result.setServiceEntrySheetItem(invoicePORefObject.getServiceEntrySheetItem());
                    result.setTaxCountry(invoicePORefObject.getTaxCountry());
                    result.setFinallyInvoiced(invoicePORefObject.isFinallyInvoiced());
                    result.setTaxDeterminationDate(invoicePORefObject.getTaxDeterminationDate());
                    result.setIn_HSNOrSACCode(invoicePORefObject.getIN_HSNOrSACCode());
                    result.setIn_CustomDutyAssessableValue(invoicePORefObject.getIN_CustomDutyAssessableValue());
                    result.setNl_ChainLiabilityStartDate(invoicePORefObject.getNL_ChainLiabilityStartDate());
                    result.setNl_ChainLiabilityEndDate(invoicePORefObject.getNL_ChainLiabilityEndDate());
                    result.setNl_ChainLiabilityDescription(invoicePORefObject.getNL_ChainLiabilityDescription());
                    result.setNl_ChainLbltyCnstrctnSiteDesc(invoicePORefObject.getNL_ChainLbltyCnstrctnSiteDesc());
                    result.setNl_ChainLiabilityDuration(invoicePORefObject.getNL_ChainLiabilityDuration());
                    result.setNl_ChainLiabilityPercent(invoicePORefObject.getNL_ChainLiabilityPercent());

                    ToSupplierInvoiceItmAcctAssgmt toSupplierInvoiceItmAcctAssgmt = invoicePostRequestDTO.new ToSupplierInvoiceItmAcctAssgmt();
                    toSupplierInvoiceItmAcctAssgmt.results = new ArrayList<>();
                    result.setTo_SupplierInvoiceItmAcctAssgmt(toSupplierInvoiceItmAcctAssgmt);

                    results.add(result);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ToSuplrInvcItemPurOrdRef invoicePORef = invoicePostRequestDTO.new ToSuplrInvcItemPurOrdRef();
        invoicePORef.setResults(results);

        invoicePostRequestDTO.setTo_SuplrInvcItemPurOrdRef(invoicePORef);

        // invoicePostRequestDTO.setTo_SelectedDeliveryNotes(null);
        // invoicePostRequestDTO.setTo_SelectedPurchaseOrders(null);
        // invoicePostRequestDTO.setTo_SelectedServiceEntrySheets(null);
        // invoicePostRequestDTO.setTo_SuplrInvcItemAsset(null);
        // invoicePostRequestDTO.setTo_SuplrInvcItemMaterial(null);
        // invoicePostRequestDTO.setTo_SuplrInvoiceAdditionalData(null);
        // invoicePostRequestDTO.setTo_SupplierInvoiceItemGLAcct(null);
        // invoicePostRequestDTO.setTo_SupplierInvoiceODN(null);
        // invoicePostRequestDTO.setTo_SupplierInvoiceTax(null);
        // invoicePostRequestDTO.setTo_SupplierInvoiceWhldgTax(null);

        return invoicePostRequestDTO;
    }
}
