package customer.btpjapanprojectdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSuplrInvcItemPurOrdRefGetResponseDTO {

    public Metadata __metadata;

    @JsonProperty("SupplierInvoice")
    public String supplierInvoice;

    @JsonProperty("FiscalYear")
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
    public boolean isNotCashDiscountLiable;

    @JsonProperty("PurchasingDocumentItemCategory")
    public String purchasingDocumentItemCategory;

    @JsonProperty("ProductType")
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
    public boolean isFinallyInvoiced;

    @JsonProperty("TaxDeterminationDate")
    public String taxDeterminationDate;

    @JsonProperty("IN_HSNOrSACCode")
    public String iN_HSNOrSACCode;

    @JsonProperty("IN_CustomDutyAssessableValue")
    public String iN_CustomDutyAssessableValue;

    @JsonProperty("NL_ChainLiabilityStartDate")
    public String nL_ChainLiabilityStartDate;

    @JsonProperty("NL_ChainLiabilityEndDate")
    public String nL_ChainLiabilityEndDate;

    @JsonProperty("NL_ChainLiabilityDescription")
    public String nL_ChainLiabilityDescription;

    @JsonProperty("NL_ChainLbltyCnstrctnSiteDesc")
    public String nL_ChainLbltyCnstrctnSiteDesc;

    @JsonProperty("NL_ChainLiabilityDuration")
    public String nL_ChainLiabilityDuration;

    @JsonProperty("NL_ChainLiabilityPercent")
    public String nL_ChainLiabilityPercent;

    // Association
    public ToSupplierInvoiceItmAcctAssgmt to_SupplierInvoiceItmAcctAssgmt;

    // Association Class

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Metadata {
        public String id;
        public String uri;
        public String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ToSupplierInvoiceItmAcctAssgmt {
        public Deferred __deferred;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Deferred {
        public String uri;
    }
}
