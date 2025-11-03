package customer.btpjapanprojectdemo.handlers;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.sap.cds.ResultBuilder;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.SupplierInvoice;
import cds.gen.mainservice.SupplierInvoiceReplicateInvoicesContext;
import cds.gen.mainservice.SupplierInvoice_;

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

    private final RestTemplate restTemplate;

    public MainServiceSupplierInvoiceHandler() {
        this.restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }

    @On(event = CqnService.EVENT_READ, entity = SupplierInvoice_.CDS_NAME)
    public void readSupplierInvoice(CdsReadEventContext context) {

        ArrayList<SupplierInvoice> supplierInvoices = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("DataServiceVersion", "2.0");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(INVOICE_USERNAME, INVOICE_PASSWORD);
        httpHeaders.set("x-csrf-token", "fetch");

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    INVOICE_READ_URL,
                    HttpMethod.GET,
                    httpEntity,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // System.out.println("INVOICE_READ" + responseEntity.getBody());
                csrfToken = responseEntity.getHeaders().get("x-csrf-token").get(0);
                cookieList = responseEntity.getHeaders().get("set-cookie");

                String response = responseEntity.getBody();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);

                JsonNode dataNode = rootNode.get("d");

                JsonNode resultNode = dataNode.get("results");
                resultNode.forEach( invoice -> {
                    SupplierInvoice supplierInvoice = SupplierInvoice.create();

                    supplierInvoice.setInvoiceNo(invoice.get("SupplierInvoice").asText());
                    supplierInvoice.setFiscalYear(invoice.get("FiscalYear").asText());
                    supplierInvoice.setInvoicingParty(invoice.get("InvoicingParty").asText());
                    supplierInvoice.setCompanyCode(invoice.get("CompanyCode").asText());

                    BigDecimal grossInvoice = new BigDecimal(invoice.get("InvoiceGrossAmount").asText());
                    supplierInvoice.setGrossInvoice(grossInvoice);

                    supplierInvoice.setDocumentCurrencyCode(invoice.get("DocumentCurrency").asText());
                    supplierInvoice.setReference(invoice.get("SupplierInvoiceIDByInvcgParty").asText());

                    
                    supplierInvoice.setPostingDate(convertJsonToDate(invoice, "PostingDate"));
                    supplierInvoice.setInvoiceDate(convertJsonToDate(invoice, "DocumentDate"));

                    supplierInvoices.add(supplierInvoice);
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultBuilder resultBuilder = ResultBuilder.selectedRows(supplierInvoices);
        if (context.getCqn().hasInlineCount()) {
            resultBuilder.inlineCount(supplierInvoices.size());
        }
        context.setResult(resultBuilder.result());
    }

    private LocalDate convertJsonToDate(JsonNode json, String Fieldname) {
        String dateBody = json.get(Fieldname).asText();
        String dateEpoch = dateBody.replaceAll("[^0-9]", "");
        Instant dateinstant = Instant.ofEpochMilli(Long.valueOf(dateEpoch));
        LocalDate date = dateinstant.atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    @On(event = SupplierInvoiceReplicateInvoicesContext.CDS_NAME, entity = SupplierInvoice_.CDS_NAME)
    public void replicateInvoice(SupplierInvoiceReplicateInvoicesContext context) {
        String httpBody = "{\r\n" + //
                "    \"CompanyCode\": \"1310\",\r\n" + //
                "    \"DocumentDate\": \"/Date(1717372800000)/\",\r\n" + //
                "    \"PostingDate\": \"/Date(1717372800000)/\",\r\n" + //
                "    \"SupplierInvoiceIDByInvcgParty\": \"CUSTREF1\",\r\n" + //
                "    \"InvoicingParty\": \"13300001\",\r\n" + //
                "    \"DocumentCurrency\": \"CNY\",\r\n" + //
                "    \"InvoiceGrossAmount\": \"2260.00\",\r\n" + //
                "    \"UnplannedDeliveryCost\": \"0.00\",\r\n" + //
                "    \"DocumentHeaderText\": \"\",\r\n" + //
                "    \"ReconciliationAccount\": \"21100000\",\r\n" + //
                "    \"ManualCashDiscount\": \"0.00\",\r\n" + //
                "    \"PaymentTerms\": \"0004\",\r\n" + //
                "    \"DueCalculationBaseDate\": \"/Date(1719705600000)/\",\r\n" + //
                "    \"CashDiscount1Percent\": \"0.000\",\r\n" + //
                "    \"CashDiscount1Days\": \"0\",\r\n" + //
                "    \"CashDiscount2Percent\": \"0.000\",\r\n" + //
                "    \"CashDiscount2Days\": \"0\",\r\n" + //
                "    \"NetPaymentDays\": \"0\",\r\n" + //
                "    \"PaymentBlockingReason\": \"\",\r\n" + //
                "    \"AccountingDocumentType\": \"RE\",\r\n" + //
                "    \"BPBankAccountInternalID\": \"\",\r\n" + //
                "    \"SupplierInvoiceStatus\": \"5\",\r\n" + //
                "    \"IndirectQuotedExchangeRate\": \"0.00000\",\r\n" + //
                "    \"DirectQuotedExchangeRate\": \"1.00000\",\r\n" + //
                "    \"StateCentralBankPaymentReason\": \"\",\r\n" + //
                "    \"SupplyingCountry\": \"\",\r\n" + //
                "    \"PaymentMethod\": \"\",\r\n" + //
                "    \"PaymentMethodSupplement\": \"\",\r\n" + //
                "    \"PaymentReference\": \"\",\r\n" + //
                "    \"InvoiceReference\": \"\",\r\n" + //
                "    \"InvoiceReferenceFiscalYear\": \"0000\",\r\n" + //
                "    \"FixedCashDiscount\": \"\",\r\n" + //
                "    \"UnplannedDeliveryCostTaxCode\": \"\",\r\n" + //
                "    \"UnplndDelivCostTaxJurisdiction\": \"\",\r\n" + //
                "    \"UnplndDeliveryCostTaxCountry\": \"\",\r\n" + //
                "    \"AssignmentReference\": \"\",\r\n" + //
                "    \"SupplierPostingLineItemText\": \"行项目文本替代\",\r\n" + //
                "    \"TaxIsCalculatedAutomatically\": true,\r\n" + //
                "    \"BusinessPlace\": \"\",\r\n" + //
                "    \"BusinessSectionCode\": \"\",\r\n" + //
                "    \"BusinessArea\": \"\",\r\n" + //
                "    \"SuplrInvcIsCapitalGoodsRelated\": false,\r\n" + //
                "    \"SupplierInvoiceIsCreditMemo\": \"\",\r\n" + //
                "    \"PaytSlipWthRefSubscriber\": \"\",\r\n" + //
                "    \"PaytSlipWthRefCheckDigit\": \"\",\r\n" + //
                "    \"PaytSlipWthRefReference\": \"\",\r\n" + //
                "    \"TaxDeterminationDate\": \"/Date(1717372800000)/\",\r\n" + //
                "    \"TaxReportingDate\": \"/Date(1717372800000)/\",\r\n" + //
                "    \"TaxFulfillmentDate\": \"/Date(1717372800000)/\",\r\n" + //
                "    \"InvoiceReceiptDate\": null,\r\n" + //
                "    \"DeliveryOfGoodsReportingCntry\": \"\",\r\n" + //
                "    \"SupplierVATRegistration\": \"\",\r\n" + //
                "    \"IsEUTriangularDeal\": false,\r\n" + //
                "    \"SuplrInvcDebitCrdtCodeDelivery\": \"\",\r\n" + //
                "    \"SuplrInvcDebitCrdtCodeReturns\": \"\",\r\n" + //
                "    \"RetentionDueDate\": null,\r\n" + //
                "    \"PaymentReason\": \"\",\r\n" + //
                "    \"HouseBank\": \"\",\r\n" + //
                "    \"HouseBankAccount\": \"\",\r\n" + //
                "    \"AlternativePayeePayer\": \"\",\r\n" + //
                "    \"IN_GSTPartner\": \"13300001\",\r\n" + //
                "    \"IN_GSTPlaceOfSupply\": \"SH\",\r\n" + //
                "    \"IN_InvoiceReferenceNumber\": \"\",\r\n" + //
                "    \"JrnlEntryCntrySpecificRef1\": \"\",\r\n" + //
                "    \"JrnlEntryCntrySpecificDate1\": null,\r\n" + //
                "    \"JrnlEntryCntrySpecificRef2\": \"\",\r\n" + //
                "    \"JrnlEntryCntrySpecificDate2\": null,\r\n" + //
                "    \"JrnlEntryCntrySpecificRef3\": \"\",\r\n" + //
                "    \"JrnlEntryCntrySpecificDate3\": null,\r\n" + //
                "    \"JrnlEntryCntrySpecificRef4\": \"\",\r\n" + //
                "    \"JrnlEntryCntrySpecificDate4\": null,\r\n" + //
                "    \"JrnlEntryCntrySpecificRef5\": \"\",\r\n" + //
                "    \"JrnlEntryCntrySpecificDate5\": null,\r\n" + //
                "    \"JrnlEntryCntrySpecificBP1\": \"\",\r\n" + //
                "    \"JrnlEntryCntrySpecificBP2\": \"\",\r\n" + //
                "    \"to_BR_SupplierInvoiceNFDocument\": null,\r\n" + //
                "    \"to_SelectedDeliveryNotes\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    },\r\n" + //
                "    \"to_SelectedPurchaseOrders\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    },\r\n" + //
                "    \"to_SelectedServiceEntrySheets\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    },\r\n" + //
                "    \"to_SuplrInvcItemAsset\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    },\r\n" + //
                "    \"to_SuplrInvcItemMaterial\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    },\r\n" + //
                "    \"to_SuplrInvcItemPurOrdRef\": {\r\n" + //
                "        \"results\": [\r\n" + //
                "            {\r\n" + //
                "                \"SupplierInvoiceItem\": \"1\",\r\n" + //
                "                \"PurchaseOrder\": \"4500000012\",\r\n" + //
                "                \"PurchaseOrderItem\": \"10\",\r\n" + //
                "                \"Plant\": \"1310\",\r\n" + //
                "                \"ReferenceDocument\": \"\",\r\n" + //
                "                \"ReferenceDocumentFiscalYear\": \"0000\",\r\n" + //
                "                \"ReferenceDocumentItem\": \"0\",\r\n" + //
                "                \"IsSubsequentDebitCredit\": \"\",\r\n" + //
                "                \"TaxCode\": \"J2\",\r\n" + //
                "                \"TaxJurisdiction\": \"\",\r\n" + //
                "                \"DocumentCurrency\": \"CNY\",\r\n" + //
                "                \"SupplierInvoiceItemAmount\": \"2000.00\",\r\n" + //
                "                \"PurchaseOrderQuantityUnit\": \"PC\",\r\n" + //
                "                \"PurchaseOrderQtyUnitSAPCode\": \"ST\",\r\n" + //
                "                \"PurchaseOrderQtyUnitISOCode\": \"PCE\",\r\n" + //
                "                \"QuantityInPurchaseOrderUnit\": \"1\",\r\n" + //
                "                \"PurchaseOrderPriceUnit\": \"PC\",\r\n" + //
                "                \"PurchaseOrderPriceUnitSAPCode\": \"ST\",\r\n" + //
                "                \"PurchaseOrderPriceUnitISOCode\": \"PCE\",\r\n" + //
                "                \"QtyInPurchaseOrderPriceUnit\": \"1\",\r\n" + //
                "                \"SuplrInvcDeliveryCostCndnType\": \"\",\r\n" + //
                "                \"SuplrInvcDeliveryCostCndnStep\": \"0\",\r\n" + //
                "                \"SuplrInvcDeliveryCostCndnCount\": \"0\",\r\n" + //
                "                \"SupplierInvoiceItemText\": \"\",\r\n" + //
                "                \"FreightSupplier\": \"\",\r\n" + //
                "                \"IsNotCashDiscountLiable\": false,\r\n" + //
                "                \"RetentionAmountInDocCurrency\": \"0.00\",\r\n" + //
                "                \"RetentionPercentage\": \"0.00\",\r\n" + //
                "                \"RetentionDueDate\": null,\r\n" + //
                "                \"SuplrInvcItmIsNotRlvtForRtntn\": false,\r\n" + //
                "                \"ServiceEntrySheet\": \"\",\r\n" + //
                "                \"ServiceEntrySheetItem\": \"0\",\r\n" + //
                "                \"TaxCountry\": \"\",\r\n" + //
                "                \"IsFinallyInvoiced\": false,\r\n" + //
                "                \"TaxDeterminationDate\": \"/Date(1717372800000)/\",\r\n" + //
                "                \"IN_HSNOrSACCode\": \"\",\r\n" + //
                "                \"IN_CustomDutyAssessableValue\": \"0.00\",\r\n" + //
                "                \"NL_ChainLiabilityStartDate\": null,\r\n" + //
                "                \"NL_ChainLiabilityEndDate\": null,\r\n" + //
                "                \"NL_ChainLiabilityDescription\": \"\",\r\n" + //
                "                \"NL_ChainLbltyCnstrctnSiteDesc\": \"\",\r\n" + //
                "                \"NL_ChainLiabilityDuration\": \"0\",\r\n" + //
                "                \"NL_ChainLiabilityPercent\": \"0.00\",\r\n" + //
                "                \"to_SupplierInvoiceItmAcctAssgmt\": {\r\n" + //
                "                    \"results\": []\r\n" + //
                "                }\r\n" + //
                "            }\r\n" + //
                "        ]\r\n" + //
                "    },\r\n" + //
                "    \"to_SupplierInvoiceItemGLAcct\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    },\r\n" + //
                "    \"to_SupplierInvoiceODN\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    },\r\n" + //
                "    \"to_SupplierInvoiceWhldgTax\": {\r\n" + //
                "        \"results\": []\r\n" + //
                "    }\r\n" + //
                "}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("DataServiceVersion", "2.0");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(INVOICE_USERNAME, INVOICE_PASSWORD);
        httpHeaders.set("x-csrf-token", csrfToken);
        httpHeaders.set("Cookie", String.join("; ", cookieList));

        HttpEntity<String> httpEntity = new HttpEntity<>( httpBody, httpHeaders);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    INVOICE_POST_URL,
                    HttpMethod.POST,
                    httpEntity,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("INVOICE_POST" + responseEntity.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        context.setCompleted();
    }
}
