package customer.btpjapanprojectdemo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PurchaseOrderItemDTO {

    @JsonProperty("PurchaseOrder")
    private String purchaseOrder;

    @JsonProperty("PurchaseOrderItem")
    private String purchaseOrderItem;

    @JsonProperty("PurchasingDocumentDeletionCode")
    private String purchasingDocumentDeletionCode;

    @JsonProperty("PurchaseOrderItemText")
    private String purchaseOrderItemText;

    @JsonProperty("Plant")
    private String plant;

    @JsonProperty("StorageLocation")
    private String storageLocation;

    @JsonProperty("MaterialGroup")
    private String materialGroup;

    @JsonProperty("PurchasingInfoRecord")
    private String purchasingInfoRecord;

    @JsonProperty("SupplierMaterialNumber")
    private String supplierMaterialNumber;

    @JsonProperty("OrderQuantity")
    private String orderQuantity;

    @JsonProperty("PurchaseOrderQuantityUnit")
    private String purchaseOrderQuantityUnit;

    @JsonProperty("OrderPriceUnit")
    private String orderPriceUnit;

    @JsonProperty("OrderPriceUnitToOrderUnitNmrtr")
    private String orderPriceUnitToOrderUnitNmrtr;

    @JsonProperty("OrdPriceUnitToOrderUnitDnmntr")
    private String ordPriceUnitToOrderUnitDnmntr;

    @JsonProperty("DocumentCurrency")
    private String documentCurrency;

    @JsonProperty("NetPriceAmount")
    private String netPriceAmount;

    @JsonProperty("NetPriceQuantity")
    private String netPriceQuantity;

    @JsonProperty("TaxCode")
    private String taxCode;

    @JsonProperty("ShippingInstruction")
    private String shippingInstruction;

    @JsonProperty("TaxDeterminationDate")
    private String taxDeterminationDate;

    @JsonProperty("TaxCountry")
    private String taxCountry;

    @JsonProperty("PriceIsToBePrinted")
    private Boolean priceIsToBePrinted;

    @JsonProperty("OverdelivTolrtdLmtRatioInPct")
    private String overdelivTolrtdLmtRatioInPct;

    @JsonProperty("UnlimitedOverdeliveryIsAllowed")
    private Boolean unlimitedOverdeliveryIsAllowed;

    @JsonProperty("UnderdelivTolrtdLmtRatioInPct")
    private String underdelivTolrtdLmtRatioInPct;

    @JsonProperty("ValuationType")
    private String valuationType;

    @JsonProperty("IsCompletelyDelivered")
    private Boolean isCompletelyDelivered;

    @JsonProperty("IsFinallyInvoiced")
    private Boolean isFinallyInvoiced;

    @JsonProperty("PurchaseOrderItemCategory")
    private String purchaseOrderItemCategory;

    @JsonProperty("AccountAssignmentCategory")
    private String accountAssignmentCategory;

    @JsonProperty("MultipleAcctAssgmtDistribution")
    private String multipleAcctAssgmtDistribution;

    @JsonProperty("PartialInvoiceDistribution")
    private String partialInvoiceDistribution;

    @JsonProperty("GoodsReceiptIsExpected")
    private Boolean goodsReceiptIsExpected;

    @JsonProperty("GoodsReceiptIsNonValuated")
    private Boolean goodsReceiptIsNonValuated;

    @JsonProperty("InvoiceIsExpected")
    private Boolean invoiceIsExpected;

    @JsonProperty("InvoiceIsGoodsReceiptBased")
    private Boolean invoiceIsGoodsReceiptBased;

    @JsonProperty("PurchaseContract")
    private String purchaseContract;

    @JsonProperty("PurchaseContractItem")
    private String purchaseContractItem;

    @JsonProperty("Customer")
    private String customer;

    @JsonProperty("Subcontractor")
    private String subcontractor;

    @JsonProperty("SupplierIsSubcontractor")
    private Boolean supplierIsSubcontractor;

    @JsonProperty("ItemNetWeight")
    private String itemNetWeight;

    @JsonProperty("ItemWeightUnit")
    private String itemWeightUnit;

    @JsonProperty("TaxJurisdiction")
    private String taxJurisdiction;

    @JsonProperty("PricingDateControl")
    private String pricingDateControl;

    @JsonProperty("ItemVolume")
    private String itemVolume;

    @JsonProperty("ItemVolumeUnit")
    private String itemVolumeUnit;

    @JsonProperty("SupplierConfirmationControlKey")
    private String supplierConfirmationControlKey;

    @JsonProperty("IncotermsClassification")
    private String incotermsClassification;

    @JsonProperty("IncotermsTransferLocation")
    private String incotermsTransferLocation;

    @JsonProperty("EvaldRcptSettlmtIsAllowed")
    private Boolean evaldRcptSettlmtIsAllowed;

    @JsonProperty("PurchaseRequisition")
    private String purchaseRequisition;

    @JsonProperty("PurchaseRequisitionItem")
    private String purchaseRequisitionItem;

    @JsonProperty("IsReturnsItem")
    private Boolean isReturnsItem;

    @JsonProperty("RequisitionerName")
    private String requisitionerName;

    @JsonProperty("ServicePackage")
    private String servicePackage;

    @JsonProperty("EarmarkedFunds")
    private String earmarkedFunds;

    @JsonProperty("EarmarkedFundsDocument")
    private String earmarkedFundsDocument;

    @JsonProperty("EarmarkedFundsItem")
    private String earmarkedFundsItem;

    @JsonProperty("EarmarkedFundsDocumentItem")
    private String earmarkedFundsDocumentItem;

    @JsonProperty("IncotermsLocation1")
    private String incotermsLocation1;

    @JsonProperty("IncotermsLocation2")
    private String incotermsLocation2;

    @JsonProperty("Material")
    private String material;

    @JsonProperty("InternationalArticleNumber")
    private String internationalArticleNumber;

    @JsonProperty("ManufacturerMaterial")
    private String manufacturerMaterial;

    @JsonProperty("ServicePerformer")
    private String servicePerformer;

    @JsonProperty("ProductType")
    private String productType;

    @JsonProperty("ExpectedOverallLimitAmount")
    private String expectedOverallLimitAmount;

    @JsonProperty("OverallLimitAmount")
    private String overallLimitAmount;

    @JsonProperty("PurContractForOverallLimit")
    private String purContractForOverallLimit;

    @JsonProperty("PurchasingParentItem")
    private String purchasingParentItem;

    @JsonProperty("Batch")
    private String batch;

    @JsonProperty("PurchasingItemIsFreeOfCharge")
    private Boolean purchasingItemIsFreeOfCharge;

    @JsonProperty("ReferenceDeliveryAddressID")
    private String referenceDeliveryAddressID;

    @JsonProperty("DeliveryAddressID")
    private String deliveryAddressID;

    @JsonProperty("DeliveryAddressName")
    private String deliveryAddressName;

    @JsonProperty("DeliveryAddressName2")
    private String deliveryAddressName2;

    @JsonProperty("DeliveryAddressFullName")
    private String deliveryAddressFullName;

    @JsonProperty("DeliveryAddressStreetName")
    private String deliveryAddressStreetName;

    @JsonProperty("DeliveryAddressHouseNumber")
    private String deliveryAddressHouseNumber;

    @JsonProperty("DeliveryAddressCityName")
    private String deliveryAddressCityName;

    @JsonProperty("DeliveryAddressPostalCode")
    private String deliveryAddressPostalCode;

    @JsonProperty("DeliveryAddressRegion")
    private String deliveryAddressRegion;

    @JsonProperty("DeliveryAddressCountry")
    private String deliveryAddressCountry;

    @JsonProperty("DeliveryAddressDistrictName")
    private String deliveryAddressDistrictName;

    @JsonProperty("DownPaymentType")
    private String downPaymentType;

    @JsonProperty("DownPaymentPercentageOfTotAmt")
    private String downPaymentPercentageOfTotAmt;

    @JsonProperty("DownPaymentAmount")
    private String downPaymentAmount;

    @JsonProperty("DownPaymentDueDate")
    private String downPaymentDueDate;

    @JsonProperty("BR_MaterialUsage")
    private String brMaterialUsage;

    @JsonProperty("BR_MaterialOrigin")
    private String brMaterialOrigin;

    @JsonProperty("BR_CFOPCategory")
    private String brCFOPCategory;

    @JsonProperty("BR_IsProducedInHouse")
    private Boolean brIsProducedInHouse;

    @JsonProperty("ConsumptionTaxCtrlCode")
    private String consumptionTaxCtrlCode;

    @JsonProperty("PurgProdCmplncSupplierStatus")
    private String purgProdCmplncSupplierStatus;

    @JsonProperty("PurgProductMarketabilityStatus")
    private String purgProductMarketabilityStatus;

    @JsonProperty("PurgSafetyDataSheetStatus")
    private String purgSafetyDataSheetStatus;

    @JsonProperty("PurgProdCmplncDngrsGoodsStatus")
    private String purgProdCmplncDngrsGoodsStatus;

    @JsonProperty("YY1_test1_PDI")
    private String yy1Test1PDI;

    @JsonProperty("YY1_test1_PDIF")
    private Integer yy1Test1PDIF;

    @JsonProperty("to_ScheduleLine")
    private PurchaseOrderDTO.NavigationResults<ScheduleLineDTO> toScheduleLine;

    @JsonProperty("to_AccountAssignment")
    private PurchaseOrderDTO.NavigationResults<AccountAssignmentDTO> toAccountAssignment;

    @JsonProperty("to_PurchaseOrderItemNote")
    private PurchaseOrderDTO.NavigationResults<PurchaseOrderItemNoteDTO> toPurchaseOrderItemNote;

}