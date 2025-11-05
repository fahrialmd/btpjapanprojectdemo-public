package customer.btpjapanprojectdemo.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePostRequestDTO {

    @JsonProperty("CompanyCode")
    public String companyCode;

    @JsonProperty("DocumentDate")
    public String documentDate;

    @JsonProperty("PostingDate")
    public String postingDate;

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
    public String bpBankAccountInternalID;

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
    public boolean euTriangularDeal;

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

    @JsonProperty("IN_GSTPartner")
    public String in_GSTPartner;

    @JsonProperty("IN_GSTPlaceOfSupply")
    public String in_GSTPlaceOfSupply;

    @JsonProperty("IN_InvoiceReferenceNumber")
    public String in_InvoiceReferenceNumber;

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

    public String to_BR_SupplierInvoiceNFDocument;

    // Association

    // public ToSelectedDeliveryNotes to_SelectedDeliveryNotes;

    // public ToSelectedPurchaseOrders to_SelectedPurchaseOrders;

    // public ToSelectedServiceEntrySheets to_SelectedServiceEntrySheets;

    // public ToSuplrInvcItemAsset to_SuplrInvcItemAsset;

    // public ToSuplrInvcItemMaterial to_SuplrInvcItemMaterial;

    public ToSuplrInvcItemPurOrdRef to_SuplrInvcItemPurOrdRef;

    // public ToSuplrInvoiceAdditionalData to_SuplrInvoiceAdditionalData;

    // public ToSupplierInvoiceItemGLAcct to_SupplierInvoiceItemGLAcct;

    // public ToSupplierInvoiceODN to_SupplierInvoiceODN;

    // public ToSupplierInvoiceTax to_SupplierInvoiceTax;

    // public ToSupplierInvoiceWhldgTax to_SupplierInvoiceWhldgTax;

    // Association Class

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSelectedDeliveryNotes {
    //     public ArrayList<Result> results;
    // }

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSelectedPurchaseOrders {
    //     public ArrayList<Result> results;
    // }

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSelectedServiceEntrySheets {
    //     public ArrayList<Result> results;
    // }

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSuplrInvcItemAsset {
    //     public ArrayList<Result> results;
    // }

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSuplrInvcItemMaterial {
    //     public ArrayList<Result> results;
    // }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSuplrInvcItemPurOrdRef {
        public ArrayList<Result> results;
    }

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSuplrInvoiceAdditionalData {
    //     @JsonProperty("SupplierInvoice")
    //     public String supplierInvoice;

    //     @JsonProperty("FiscalYear")
    //     public String fiscalYear;

    //     @JsonProperty("InvoicingPartyName1")
    //     public String invoicingPartyName1;

    //     @JsonProperty("InvoicingPartyName2")
    //     public String invoicingPartyName2;

    //     @JsonProperty("InvoicingPartyName3")
    //     public String invoicingPartyName3;

    //     @JsonProperty("InvoicingPartyName4")
    //     public String invoicingPartyName4;

    //     @JsonProperty("PostalCode")
    //     public String postalCode;

    //     @JsonProperty("CityName")
    //     public String cityName;

    //     @JsonProperty("Country")
    //     public String country;

    //     @JsonProperty("StreetAddressName")
    //     public String streetAddressName;

    //     @JsonProperty("POBox")
    //     public String pOBox;

    //     @JsonProperty("POBoxPostalCode")
    //     public String pOBoxPostalCode;

    //     @JsonProperty("PostOfficeBankAccount")
    //     public String postOfficeBankAccount;

    //     @JsonProperty("BankAccount")
    //     public String bankAccount;

    //     @JsonProperty("Bank")
    //     public String bank;

    //     @JsonProperty("BankCountry")
    //     public String bankCountry;

    //     @JsonProperty("TaxID1")
    //     public String taxID1;

    //     @JsonProperty("TaxID2")
    //     public String taxID2;

    //     @JsonProperty("TaxID3")
    //     public String taxID3;

    //     @JsonProperty("TaxID4")
    //     public String taxID4;

    //     @JsonProperty("TaxID5")
    //     public String taxID5;

    //     @JsonProperty("OneTmeAccountIsVATLiable")
    //     public boolean oneTmeAccountIsVATLiable;

    //     @JsonProperty("OneTmeAcctIsEqualizationTxSubj")
    //     public boolean oneTmeAcctIsEqualizationTxSubj;

    //     @JsonProperty("Region")
    //     public String region;

    //     @JsonProperty("BankControlKey")
    //     public String bankControlKey;

    //     @JsonProperty("DataExchangeInstructionKey")
    //     public String dataExchangeInstructionKey;

    //     @JsonProperty("DataMediumExchangeIndicator")
    //     public String dataMediumExchangeIndicator;

    //     @JsonProperty("LanguageCode")
    //     public String languageCode;

    //     @JsonProperty("IsOneTimeAccount")
    //     public boolean isOneTimeAccount;

    //     @JsonProperty("PaymentRecipient")
    //     public String paymentRecipient;

    //     @JsonProperty("AccountTaxType")
    //     public String accountTaxType;

    //     @JsonProperty("TaxNumberType")
    //     public String taxNumberType;

    //     @JsonProperty("IsNaturalPerson")
    //     public boolean isNaturalPerson;

    //     @JsonProperty("BankDetailReference")
    //     public String bankDetailReference;

    //     @JsonProperty("RepresentativeName")
    //     public String representativeName;

    //     @JsonProperty("BusinessType")
    //     public String businessType;

    //     @JsonProperty("IndustryType")
    //     public String industryType;

    //     @JsonProperty("FormOfAddressName")
    //     public String formOfAddressName;

    //     @JsonProperty("VATRegistration")
    //     public String vATRegistration;

    //     @JsonProperty("OneTimeAcctCntrySpecificRef1")
    //     public String oneTimeAcctCntrySpecificRef1;

    //     @JsonProperty("IBAN")
    //     public String iBAN;

    //     @JsonProperty("SWIFTCode")
    //     public String sWIFTCode;

    //     @JsonProperty("OneTimeBusinessPartnerEmail")
    //     public String oneTimeBusinessPartnerEmail;

    // }

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSupplierInvoiceItemGLAcct {
    //     public ArrayList<Result> results;
    // }
    
    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSupplierInvoiceODN {
        //     public ArrayList<Result> results;
        // }
        
        // @Data
        // @NoArgsConstructor
        // @AllArgsConstructor
    // public class ToSupplierInvoiceTax {
        //     public ArrayList<Result> results;
        // }

    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor
    // public class ToSupplierInvoiceWhldgTax {
    //     public ArrayList<Result> results;
    // }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        // @JsonProperty("InboundDeliveryNote")
        // public String inboundDeliveryNote;
        
        @JsonProperty("TaxCode")
        public String taxCode;
        
        @JsonProperty("DocumentCurrency")
        public String documentCurrency;
        
        // @JsonProperty("TaxAmount")
        // public String taxAmount;
        
        // @JsonProperty("TaxBaseAmountInTransCrcy")
        // public String taxBaseAmountInTransCrcy;
        
        @JsonProperty("TaxJurisdiction")
        public String taxJurisdiction;
        
        @JsonProperty("TaxCountry")
        public String taxCountry;
        
        @JsonProperty("TaxDeterminationDate")
        public String taxDeterminationDate;
        
        // @JsonProperty("TaxRateValidityStartDate")
        // public String taxRateValidityStartDate;
        
        // @JsonProperty("WithholdingTaxType")
        // public String withholdingTaxType;
        
        // @JsonProperty("WithholdingTaxCode")
        // public String withholdingTaxCode;
        
        // @JsonProperty("WithholdingTaxBaseAmount")
        // public String withholdingTaxBaseAmount;
        
        // @JsonProperty("ManuallyEnteredWhldgTaxAmount")
        // public String manuallyEnteredWhldgTaxAmount;
        
        // @JsonProperty("WhldgTaxIsEnteredManually")
        // public boolean whldgTaxIsEnteredManually;
        
        // @JsonProperty("WhldgTaxBaseIsEnteredManually")
        // public boolean whldgTaxBaseIsEnteredManually;

        @JsonProperty("PurchaseOrder")
        public String purchaseOrder;
        
        @JsonProperty("PurchaseOrderItem")
        public String purchaseOrderItem;

        @JsonProperty("ServiceEntrySheet")
        public String serviceEntrySheet;

        @JsonProperty("ServiceEntrySheetItem")
        public String serviceEntrySheetItem;

        @JsonProperty("SupplierInvoiceItem")
        public String supplierInvoiceItem;

        // @JsonProperty("CompanyCode")
        // public String companyCode;

        // @JsonProperty("MasterFixedAsset")
        // public String masterFixedAsset;

        // @JsonProperty("FixedAsset")
        // public String fixedAsset;

        // @JsonProperty("ProfitCenter")
        // public String profitCenter;

        @JsonProperty("SupplierInvoiceItemAmount")
        public String supplierInvoiceItemAmount;
        
        // @JsonProperty("DebitCreditCode")
        // public String debitCreditCode;
        
        @JsonProperty("SupplierInvoiceItemText")
        public String supplierInvoiceItemText;
        
        // @JsonProperty("AssignmentReference")
        // public String assignmentReference;
        
        @JsonProperty("IsNotCashDiscountLiable")
        public boolean notCashDiscountLiable;
        
        // @JsonProperty("AssetValueDate")
        // public String assetValueDate;
        
        // @JsonProperty("QuantityUnit")
        // public String quantityUnit;
        
        // @JsonProperty("SuplrInvcItmQtyUnitSAPCode")
        // public String suplrInvcItmQtyUnitSAPCode;
        
        // @JsonProperty("SuplrInvcItmQtyUnitISOCode")
        // public String suplrInvcItmQtyUnitISOCode;
        
        // @JsonProperty("Quantity")
        // public String quantity;
        
        // @JsonProperty("Material")
        // public String material;

        // @JsonProperty("ValuationArea")
        // public String valuationArea;
        
        // @JsonProperty("InventoryValuationType")
        // public String inventoryValuationType;
        
        @JsonProperty("Plant")
        public String plant;
        
        @JsonProperty("ReferenceDocument")
        public String referenceDocument;
        
        @JsonProperty("ReferenceDocumentFiscalYear")
        public String referenceDocumentFiscalYear;
        
        @JsonProperty("ReferenceDocumentItem")
        public String referenceDocumentItem;
        
        @JsonProperty("IsSubsequentDebitCredit")
        public String isSubsequentDebitCredit;
        
        @JsonProperty("PurchaseOrderQuantityUnit")
        public String purchaseOrderQuantityUnit;
        
        @JsonProperty("PurchaseOrderQtyUnitSAPCode")
        public String purchaseOrderQtyUnitSAPCode;
        
        @JsonProperty("PurchaseOrderQtyUnitISOCode")
        public String purchaseOrderQtyUnitISOCode;
        
        @JsonProperty("QuantityInPurchaseOrderUnit")
        public String quantityInPurchaseOrderUnit;
        
        @JsonProperty("PurchaseOrderPriceUnit")
        public String purchaseOrderPriceUnit;
        
        @JsonProperty("PurchaseOrderPriceUnitSAPCode")
        public String purchaseOrderPriceUnitSAPCode;
        
        @JsonProperty("PurchaseOrderPriceUnitISOCode")
        public String purchaseOrderPriceUnitISOCode;
        
        @JsonProperty("QtyInPurchaseOrderPriceUnit")
        public String qtyInPurchaseOrderPriceUnit;
        
        @JsonProperty("SuplrInvcDeliveryCostCndnType")
        public String suplrInvcDeliveryCostCndnType;
        
        @JsonProperty("SuplrInvcDeliveryCostCndnStep")
        public String suplrInvcDeliveryCostCndnStep;
        
        @JsonProperty("SuplrInvcDeliveryCostCndnCount")
        public String suplrInvcDeliveryCostCndnCount;
        
        @JsonProperty("FreightSupplier")
        public String freightSupplier;
        
        @JsonProperty("RetentionAmountInDocCurrency")
        public String retentionAmountInDocCurrency;

        @JsonProperty("RetentionPercentage")
        public String retentionPercentage;
        
        @JsonProperty("RetentionDueDate")
        public String retentionDueDate;
        
        @JsonProperty("SuplrInvcItmIsNotRlvtForRtntn")
        public boolean suplrInvcItmIsNotRlvtForRtntn;
        
        @JsonProperty("IsFinallyInvoiced")
        public boolean finallyInvoiced;
        
        @JsonProperty("IN_HSNOrSACCode")
        public String in_HSNOrSACCode;
        
        @JsonProperty("IN_CustomDutyAssessableValue")
        public String in_CustomDutyAssessableValue;
        
        @JsonProperty("NL_ChainLiabilityStartDate")
        public String nl_ChainLiabilityStartDate;
        
        @JsonProperty("NL_ChainLiabilityEndDate")
        public String nl_ChainLiabilityEndDate;

        @JsonProperty("NL_ChainLiabilityDescription")
        public String nl_ChainLiabilityDescription;
        
        @JsonProperty("NL_ChainLbltyCnstrctnSiteDesc")
        public String nl_ChainLbltyCnstrctnSiteDesc;
        
        @JsonProperty("NL_ChainLiabilityDuration")
        public String nl_ChainLiabilityDuration;
        
        @JsonProperty("NL_ChainLiabilityPercent")
        public String nl_ChainLiabilityPercent;
        
        public ToSupplierInvoiceItmAcctAssgmt to_SupplierInvoiceItmAcctAssgmt;
        
        // @JsonProperty("OrdinalNumber")
        // public String ordinalNumber;
        
        // @JsonProperty("CostCenter")
        // public String costCenter;
        
        // @JsonProperty("ControllingArea")
        // public String controllingArea;
        
        // @JsonProperty("BusinessArea")
        // public String businessArea;
        
        // @JsonProperty("FunctionalArea")
        // public String functionalArea;
        
        // @JsonProperty("GLAccount")
        // public String gLAccount;
        
        // @JsonProperty("SalesOrder")
        // public String salesOrder;
        
        // @JsonProperty("SalesOrderItem")
        // public String salesOrderItem;
        
        // @JsonProperty("CostObject")
        // public String costObject;
        
        // @JsonProperty("WBSElement")
        // public String wBSElement;
        
        // @JsonProperty("SuplrInvcAcctAssignmentAmount")
        // public String suplrInvcAcctAssignmentAmount;
        
        // @JsonProperty("AccountAssignmentNumber")
        // public String accountAssignmentNumber;
        
        // @JsonProperty("AccountAssignmentIsUnplanned")
        // public boolean accountAssignmentIsUnplanned;
        
        // @JsonProperty("PersonnelNumber")
        // public String personnelNumber;
        
        // @JsonProperty("InternalOrder")
        // public String internalOrder;
        
        // @JsonProperty("ProjectNetworkInternalID")
        // public String projectNetworkInternalID;
        
        // @JsonProperty("NetworkActivityInternalID")
        // public String networkActivityInternalID;
        
        // @JsonProperty("ProjectNetwork")
        // public String projectNetwork;
        
        // @JsonProperty("NetworkActivity")
        // public String networkActivity;
        
        // @JsonProperty("CommitmentItem")
        // public String commitmentItem;

        // @JsonProperty("FundsCenter")
        // public String fundsCenter;
        
        // @JsonProperty("Fund")
        // public String fund;
        
        // @JsonProperty("GrantID")
        // public String grantID;
        
        // @JsonProperty("SuplrInvcAccountAssignmentText")
        // public String suplrInvcAccountAssignmentText;
        
        // @JsonProperty("ProfitabilitySegment")
        // public String profitabilitySegment;

        // @JsonProperty("BudgetPeriod")
        // public String budgetPeriod;
        
        // @JsonProperty("CostCtrActivityType")
        // public String costCtrActivityType;
        
        // @JsonProperty("BusinessProcess")
        // public String businessProcess;
        
        // @JsonProperty("WorkItem")
        // public String workItem;
        
        // @JsonProperty("PartnerBusinessArea")
        // public String partnerBusinessArea;
        
        // @JsonProperty("FinancialTransactionType")
        // public String financialTransactionType;
        
        // @JsonProperty("EarmarkedFundsDocument")
        // public String earmarkedFundsDocument;
        
        // @JsonProperty("EarmarkedFundsDocumentItem")
        // public String earmarkedFundsDocumentItem;
        
        // @JsonProperty("ServiceDocument")
        // public String serviceDocument;
        
        // @JsonProperty("ServiceDocumentItem")
        // public String serviceDocumentItem;
        
        // @JsonProperty("ServiceDocumentType")
        // public String serviceDocumentType;

        // @JsonProperty("OfficialDocumentNumberCountry")
        // public String officialDocumentNumberCountry;
        
        // @JsonProperty("OfficialDocumentNumberType")
        // public String officialDocumentNumberType;
        
        // @JsonProperty("OfficialDocumentNumber")
        // public String officialDocumentNumber;
        
        // @JsonProperty("ODNLegalDateTimeText")
        // public String oDNLegalDateTimeText;
        
        // @JsonProperty("OfficialDocumentNumberIntType")
        // public String officialDocumentNumberIntType;
    }
    
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public class ToSupplierInvoiceItmAcctAssgmt {
            public ArrayList<Result> results;
        }
    
}