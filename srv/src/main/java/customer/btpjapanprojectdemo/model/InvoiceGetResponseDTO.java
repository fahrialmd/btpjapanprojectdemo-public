package customer.btpjapanprojectdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceGetResponseDTO {

    public Metadata __metadata;

    @JsonProperty("SupplierInvoice")
    public String supplierInvoice;

    @JsonProperty("FiscalYear")
    public String fiscalYear;

    @JsonProperty("CompanyCode")
    public String companyCode;

    @JsonProperty("DocumentDate")
    public String documentDate;

    @JsonProperty("PostingDate")
    public String postingDate;

    @JsonProperty("CreationDate")
    public String creationDate;

    @JsonProperty("SuplrInvcLstChgDteTmeTxt")
    public String suplrInvcLstChgDteTmeTxt;

    @JsonProperty("SupplierInvoiceIDByInvcgParty")
    public String supplierInvoiceIDByInvcgParty;

    @JsonProperty("InvoicingParty")
    public String invoicingParty;

    @JsonProperty("DocumentCurrency")
    public String documentCurrency;

    @JsonProperty("InvoiceGrossAmount")
    public String invoiceGrossAmount;

    @JsonProperty("UnplannedDeliveryCost")
    public String unplannedDeliveryCost;

    @JsonProperty("DocumentHeaderText")
    public String documentHeaderText;

    @JsonProperty("ReconciliationAccount")
    public String reconciliationAccount;

    @JsonProperty("ManualCashDiscount")
    public String manualCashDiscount;

    @JsonProperty("PaymentTerms")
    public String paymentTerms;

    @JsonProperty("DueCalculationBaseDate")
    public String dueCalculationBaseDate;

    @JsonProperty("CashDiscount1Percent")
    public String cashDiscount1Percent;

    @JsonProperty("CashDiscount1Days")
    public String cashDiscount1Days;

    @JsonProperty("CashDiscount2Percent")
    public String cashDiscount2Percent;

    @JsonProperty("CashDiscount2Days")
    public String cashDiscount2Days;

    @JsonProperty("NetPaymentDays")
    public String netPaymentDays;

    @JsonProperty("PaymentBlockingReason")
    public String paymentBlockingReason;

    @JsonProperty("AccountingDocumentType")
    public String accountingDocumentType;

    @JsonProperty("BPBankAccountInternalID")
    public String bPBankAccountInternalID;

    @JsonProperty("SupplierInvoiceStatus")
    public String supplierInvoiceStatus;

    @JsonProperty("IndirectQuotedExchangeRate")
    public String indirectQuotedExchangeRate;

    @JsonProperty("DirectQuotedExchangeRate")
    public String directQuotedExchangeRate;

    @JsonProperty("StateCentralBankPaymentReason")
    public String stateCentralBankPaymentReason;

    @JsonProperty("SupplyingCountry")
    public String supplyingCountry;

    @JsonProperty("PaymentMethod")
    public String paymentMethod;

    @JsonProperty("PaymentMethodSupplement")
    public String paymentMethodSupplement;

    @JsonProperty("PaymentReference")
    public String paymentReference;

    @JsonProperty("InvoiceReference")
    public String invoiceReference;

    @JsonProperty("InvoiceReferenceFiscalYear")
    public String invoiceReferenceFiscalYear;

    @JsonProperty("FixedCashDiscount")
    public String fixedCashDiscount;

    @JsonProperty("UnplannedDeliveryCostTaxCode")
    public String unplannedDeliveryCostTaxCode;

    @JsonProperty("UnplndDelivCostTaxJurisdiction")
    public String unplndDelivCostTaxJurisdiction;

    @JsonProperty("UnplndDeliveryCostTaxCountry")
    public String unplndDeliveryCostTaxCountry;

    @JsonProperty("AssignmentReference")
    public String assignmentReference;

    @JsonProperty("SupplierPostingLineItemText")
    public String supplierPostingLineItemText;

    @JsonProperty("TaxIsCalculatedAutomatically")
    public boolean taxIsCalculatedAutomatically;

    @JsonProperty("BusinessPlace")
    public String businessPlace;

    @JsonProperty("BusinessSectionCode")
    public String businessSectionCode;

    @JsonProperty("BusinessArea")
    public String businessArea;

    @JsonProperty("SuplrInvcIsCapitalGoodsRelated")
    public boolean suplrInvcIsCapitalGoodsRelated;

    @JsonProperty("SupplierInvoiceIsCreditMemo")
    public String supplierInvoiceIsCreditMemo;

    @JsonProperty("PaytSlipWthRefSubscriber")
    public String paytSlipWthRefSubscriber;

    @JsonProperty("PaytSlipWthRefCheckDigit")
    public String paytSlipWthRefCheckDigit;

    @JsonProperty("PaytSlipWthRefReference")
    public String paytSlipWthRefReference;

    @JsonProperty("TaxDeterminationDate")
    public String taxDeterminationDate;

    @JsonProperty("TaxReportingDate")
    public String taxReportingDate;

    @JsonProperty("TaxFulfillmentDate")
    public String taxFulfillmentDate;

    @JsonProperty("InvoiceReceiptDate")
    public String invoiceReceiptDate;

    @JsonProperty("DeliveryOfGoodsReportingCntry")
    public String deliveryOfGoodsReportingCntry;

    @JsonProperty("SupplierVATRegistration")
    public String supplierVATRegistration;

    @JsonProperty("IsEUTriangularDeal")
    public boolean isEUTriangularDeal;

    @JsonProperty("SuplrInvcDebitCrdtCodeDelivery")
    public String suplrInvcDebitCrdtCodeDelivery;

    @JsonProperty("SuplrInvcDebitCrdtCodeReturns")
    public String suplrInvcDebitCrdtCodeReturns;

    @JsonProperty("RetentionDueDate")
    public String retentionDueDate;

    @JsonProperty("PaymentReason")
    public String paymentReason;

    @JsonProperty("HouseBank")
    public String houseBank;

    @JsonProperty("HouseBankAccount")
    public String houseBankAccount;

    @JsonProperty("AlternativePayeePayer")
    public String alternativePayeePayer;

    @JsonProperty("SupplierInvoiceOrigin")
    public String supplierInvoiceOrigin;

    @JsonProperty("ReverseDocument")
    public String reverseDocument;

    @JsonProperty("ReverseDocumentFiscalYear")
    public String reverseDocumentFiscalYear;

    @JsonProperty("IsReversal")
    public boolean isReversal;

    @JsonProperty("IsReversed")
    public boolean isReversed;

    @JsonProperty("SupplierInvoicePaymentStatus")
    public String supplierInvoicePaymentStatus;

    @JsonProperty("IN_GSTPartner")
    public String iN_GSTPartner;

    @JsonProperty("IN_GSTPlaceOfSupply")
    public String iN_GSTPlaceOfSupply;

    @JsonProperty("IN_InvoiceReferenceNumber")
    public String iN_InvoiceReferenceNumber;

    @JsonProperty("JrnlEntryCntrySpecificRef1")
    public String jrnlEntryCntrySpecificRef1;

    @JsonProperty("JrnlEntryCntrySpecificDate1")
    public String jrnlEntryCntrySpecificDate1;

    @JsonProperty("JrnlEntryCntrySpecificRef2")
    public String jrnlEntryCntrySpecificRef2;

    @JsonProperty("JrnlEntryCntrySpecificDate2")
    public String jrnlEntryCntrySpecificDate2;

    @JsonProperty("JrnlEntryCntrySpecificRef3")
    public String jrnlEntryCntrySpecificRef3;

    @JsonProperty("JrnlEntryCntrySpecificDate3")
    public String jrnlEntryCntrySpecificDate3;

    @JsonProperty("JrnlEntryCntrySpecificRef4")
    public String jrnlEntryCntrySpecificRef4;

    @JsonProperty("JrnlEntryCntrySpecificDate4")
    public String jrnlEntryCntrySpecificDate4;

    @JsonProperty("JrnlEntryCntrySpecificRef5")
    public String jrnlEntryCntrySpecificRef5;

    @JsonProperty("JrnlEntryCntrySpecificDate5")
    public String jrnlEntryCntrySpecificDate5;

    @JsonProperty("JrnlEntryCntrySpecificBP1")
    public String jrnlEntryCntrySpecificBP1;

    @JsonProperty("JrnlEntryCntrySpecificBP2")
    public String jrnlEntryCntrySpecificBP2;

    // Association
    public ToBRSupplierInvoiceNFDocument to_BR_SupplierInvoiceNFDocument;

    public ToSelectedDeliveryNotes to_SelectedDeliveryNotes;

    public ToSelectedPurchaseOrders to_SelectedPurchaseOrders;

    public ToSelectedServiceEntrySheets to_SelectedServiceEntrySheets;

    public ToSuplrInvcItemAsset to_SuplrInvcItemAsset;

    public ToSuplrInvcItemMaterial to_SuplrInvcItemMaterial;

    public ToSuplrInvcItemPurOrdRef to_SuplrInvcItemPurOrdRef;

    public ToSuplrInvoiceAdditionalData to_SuplrInvoiceAdditionalData;

    public ToSupplierInvoiceItemGLAcct to_SupplierInvoiceItemGLAcct;

    public ToSupplierInvoiceODN to_SupplierInvoiceODN;

    public ToSupplierInvoiceTax to_SupplierInvoiceTax;

    public ToSupplierInvoiceWhldgTax to_SupplierInvoiceWhldgTax;

    // Association Class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToBRSupplierInvoiceNFDocument {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSelectedDeliveryNotes {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSelectedPurchaseOrders {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSelectedServiceEntrySheets {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSuplrInvcItemAsset {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSuplrInvcItemMaterial {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSuplrInvcItemPurOrdRef {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSuplrInvoiceAdditionalData {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSupplierInvoiceItemGLAcct {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSupplierInvoiceODN {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSupplierInvoiceTax {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSupplierInvoiceWhldgTax {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Deferred {
        public String uri;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Metadata {
        public String id;
        public String uri;
        public String type;
    }
}
