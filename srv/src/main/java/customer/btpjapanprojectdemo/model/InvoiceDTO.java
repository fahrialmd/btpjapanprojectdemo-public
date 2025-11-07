package customer.btpjapanprojectdemo.model;

import java.util.ArrayList;

import org.springframework.web.service.invoker.HttpRequestValues.Metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDTO {

    @JsonProperty(value = "SupplierInvoice", access = Access.WRITE_ONLY)
    public String supplierInvoice;

    @JsonProperty(value = "FiscalYear", access = Access.WRITE_ONLY)
    public String fiscalYear;

    @JsonProperty("CompanyCode")
    public String companyCode;

    @JsonProperty("DocumentDate")
    public String documentDate;

    @JsonProperty("PostingDate")
    public String postingDate;

    @JsonProperty(value = "CreationDate", access = Access.WRITE_ONLY)
    public String creationDate;

    @JsonProperty(value = "SuplrInvcLstChgDteTmeTxt", access = Access.WRITE_ONLY)
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

    @JsonProperty(value = "SupplierInvoiceOrigin", access = Access.WRITE_ONLY)
    public String supplierInvoiceOrigin;

    @JsonProperty(value = "ReverseDocument", access = Access.WRITE_ONLY)
    public String reverseDocument;

    @JsonProperty(value = "ReverseDocumentFiscalYear", access = Access.WRITE_ONLY)
    public String reverseDocumentFiscalYear;

    @JsonProperty(value = "IsReversal", access = Access.WRITE_ONLY)
    public boolean reversal;

    @JsonProperty(value = "IsReversed", access = Access.WRITE_ONLY)
    public boolean reversed;

    @JsonProperty(value = "SupplierInvoicePaymentStatus", access = Access.WRITE_ONLY)
    public String supplierInvoicePaymentStatus;

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

    // Association

    public ToSuplrInvcItemPurOrdRef to_SuplrInvcItemPurOrdRef;

    @JsonProperty(access = Access.WRITE_ONLY)
    public ToSupplierInvoiceTax to_SupplierInvoiceTax;

    // Association Class

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ToSuplrInvcItemPurOrdRef {
        public ArrayList<SuplrInvcItemPurOrdRefResult> results;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ToSupplierInvoiceTax {
        public ArrayList<SupplierInvoiceTaxResult> results;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SuplrInvcItemPurOrdRefResult {

        @JsonProperty(value = "SupplierInvoice", access = Access.WRITE_ONLY)
        public String supplierInvoice;

        @JsonProperty(value = "FiscalYear", access = Access.WRITE_ONLY)
        public String fiscalYear;

        @JsonProperty("SupplierInvoiceItem")
        public String supplierInvoiceItem;

        @JsonProperty("PurchaseOrder")
        public String purchaseOrder;

        @JsonProperty("PurchaseOrderItem")
        public String purchaseOrderItem;

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

        @JsonProperty("TaxCode")
        public String taxCode;

        @JsonProperty("TaxJurisdiction")
        public String taxJurisdiction;

        @JsonProperty("DocumentCurrency")
        public String documentCurrency;

        @JsonProperty("SupplierInvoiceItemAmount")
        public String supplierInvoiceItemAmount;

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

        @JsonProperty("SupplierInvoiceItemText")
        public String supplierInvoiceItemText;

        @JsonProperty("FreightSupplier")
        public String freightSupplier;

        @JsonProperty("IsNotCashDiscountLiable")
        public boolean notCashDiscountLiable;

        @JsonProperty(value = "PurchasingDocumentItemCategory", access = Access.WRITE_ONLY)
        public String purchasingDocumentItemCategory;

        @JsonProperty(value = "ProductType", access = Access.WRITE_ONLY)
        public String productType;

        @JsonProperty("RetentionAmountInDocCurrency")
        public String retentionAmountInDocCurrency;

        @JsonProperty("RetentionPercentage")
        public String retentionPercentage;

        @JsonProperty("RetentionDueDate")
        public String retentionDueDate;

        @JsonProperty("SuplrInvcItmIsNotRlvtForRtntn")
        public boolean suplrInvcItmIsNotRlvtForRtntn;

        @JsonProperty("ServiceEntrySheet")
        public String serviceEntrySheet;

        @JsonProperty("ServiceEntrySheetItem")
        public String serviceEntrySheetItem;

        @JsonProperty("TaxCountry")
        public String taxCountry;

        @JsonProperty("IsFinallyInvoiced")
        public boolean finallyInvoiced;

        @JsonProperty("TaxDeterminationDate")
        public String taxDeterminationDate;

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
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ToSupplierInvoiceItmAcctAssgmt {
        public ArrayList<SupplierInvoiceItmAcctAssgmtResult> results;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SupplierInvoiceItmAcctAssgmtResult {

        @JsonProperty(value = "SupplierInvoice", access = Access.WRITE_ONLY)
        public String supplierInvoice;

        @JsonProperty(value = "FiscalYear", access = Access.WRITE_ONLY)
        public String fiscalYear;

        @JsonProperty("SupplierInvoiceItem")
        public String supplierInvoiceItem;

        @JsonProperty("OrdinalNumber")
        public String ordinalNumber;

        @JsonProperty("CostCenter")
        public String costCenter;

        @JsonProperty("ControllingArea")
        public String controllingArea;

        @JsonProperty("BusinessArea")
        public String businessArea;

        @JsonProperty("ProfitCenter")
        public String profitCenter;

        @JsonProperty("FunctionalArea")
        public String functionalArea;

        @JsonProperty("GLAccount")
        public String glAccount;

        @JsonProperty("SalesOrder")
        public String salesOrder;

        @JsonProperty("SalesOrderItem")
        public String salesOrderItem;

        @JsonProperty("CostObject")
        public String costObject;

        @JsonProperty(value = "CostCtrActivityType", access = Access.WRITE_ONLY)
        public String costCtrActivityType;

        @JsonProperty(value = "BusinessProcess", access = Access.WRITE_ONLY)
        public String businessProcess;

        @JsonProperty("WBSElement")
        public String wbSElement;

        @JsonProperty("DocumentCurrency")
        public String documentCurrency;

        @JsonProperty("SuplrInvcAcctAssignmentAmount")
        public String suplrInvcAcctAssignmentAmount;

        @JsonProperty("PurchaseOrderQuantityUnit")
        public String purchaseOrderQuantityUnit;

        @JsonProperty("PurchaseOrderQtyUnitSAPCode")
        public String purchaseOrderQtyUnitSAPCode;

        @JsonProperty("PurchaseOrderQtyUnitISOCode")
        public String purchaseOrderQtyUnitISOCode;

        @JsonProperty("Quantity")
        public String quantity;

        @JsonProperty("TaxCode")
        public String taxCode;

        @JsonProperty("AccountAssignmentNumber")
        public String accountAssignmentNumber;

        @JsonProperty("AccountAssignmentIsUnplanned")
        public boolean accountAssignmentIsUnplanned;

        @JsonProperty("PersonnelNumber")
        public String personnelNumber;

        @JsonProperty(value = "WorkItem", access = Access.WRITE_ONLY)
        public String workItem;

        @JsonProperty("MasterFixedAsset")
        public String masterFixedAsset;

        @JsonProperty("FixedAsset")
        public String fixedAsset;

        @JsonProperty(value = "DebitCreditCode", access = Access.WRITE_ONLY)
        public String debitCreditCode;

        @JsonProperty("TaxJurisdiction")
        public String taxJurisdiction;

        @JsonProperty("InternalOrder")
        public String internalOrder;

        @JsonProperty("ProjectNetworkInternalID")
        public String projectNetworkInternalID;

        @JsonProperty("NetworkActivityInternalID")
        public String networkActivityInternalID;

        @JsonProperty("ProjectNetwork")
        public String projectNetwork;

        @JsonProperty("NetworkActivity")
        public String networkActivity;

        @JsonProperty("CommitmentItem")
        public String commitmentItem;

        @JsonProperty("FundsCenter")
        public String fundsCenter;

        @JsonProperty("Fund")
        public String fund;

        @JsonProperty("GrantID")
        public String grantID;

        @JsonProperty(value = "PartnerBusinessArea", access = Access.WRITE_ONLY)
        public String partnerBusinessArea;

        @JsonProperty(value = "CompanyCode", access = Access.WRITE_ONLY)
        public String companyCode;

        @JsonProperty("SuplrInvcAccountAssignmentText")
        public String suplrInvcAccountAssignmentText;

        @JsonProperty("PurchaseOrderPriceUnit")
        public String purchaseOrderPriceUnit;

        @JsonProperty("PurchaseOrderPriceUnitSAPCode")
        public String purchaseOrderPriceUnitSAPCode;

        @JsonProperty("PurchaseOrderPriceUnitISOCode")
        public String purchaseOrderPriceUnitISOCode;

        @JsonProperty("QuantityInPurchaseOrderUnit")
        public String quantityInPurchaseOrderUnit;

        @JsonProperty("ProfitabilitySegment")
        public String profitabilitySegment;

        @JsonProperty("BudgetPeriod")
        public String budgetPeriod;

        @JsonProperty("TaxCountry")
        public String taxCountry;

        @JsonProperty(value = "TaxDeterminationDate", access = Access.WRITE_ONLY)
        public String taxDeterminationDate;

    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SupplierInvoiceTaxResult {

        @JsonProperty(value = "SupplierInvoice", access = Access.WRITE_ONLY)
        public String supplierInvoice;

        @JsonProperty(value = "FiscalYear", access = Access.WRITE_ONLY)
        public String fiscalYear;

        @JsonProperty("TaxCode")
        public String taxCode;

        @JsonProperty("SupplierInvoiceTaxCounter")
        public String supplierInvoiceTaxCounter;

        @JsonProperty("DocumentCurrency")
        public String documentCurrency;

        @JsonProperty("TaxAmount")
        public String taxAmount;

        @JsonProperty("TaxBaseAmountInTransCrcy")
        public String taxBaseAmountInTransCrcy;

        @JsonProperty("TaxJurisdiction")
        public String taxJurisdiction;

        @JsonProperty("TaxCountry")
        public String taxCountry;

        @JsonProperty("TaxDeterminationDate")
        public String taxDeterminationDate;

        @JsonProperty("TaxRateValidityStartDate")
        public String taxRateValidityStartDate;
    }
}
