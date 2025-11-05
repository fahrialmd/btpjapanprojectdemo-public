package customer.btpjapanprojectdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseOrderDTO {

    @JsonProperty("PurchaseOrder")
    private String purchaseOrder;

    @JsonProperty("CompanyCode")
    private String companyCode;

    @JsonProperty("PurchaseOrderType")
    private String purchaseOrderType;

    @JsonProperty("PurchasingDocumentDeletionCode")
    private String purchasingDocumentDeletionCode;

    @JsonProperty("PurchasingProcessingStatus")
    private String purchasingProcessingStatus;

    @JsonProperty("CreatedByUser")
    private String createdByUser;

    @JsonProperty("CreationDate")
    private String creationDate;

    @JsonProperty("LastChangeDateTime")
    private String lastChangeDateTime;

    @JsonProperty("Supplier")
    private String supplier;

    @JsonProperty("PurchaseOrderSubtype")
    private String purchaseOrderSubtype;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("PaymentTerms")
    private String paymentTerms;

    @JsonProperty("CashDiscount1Days")
    private String cashDiscount1Days;

    @JsonProperty("CashDiscount2Days")
    private String cashDiscount2Days;

    @JsonProperty("NetPaymentDays")
    private String netPaymentDays;

    @JsonProperty("CashDiscount1Percent")
    private String cashDiscount1Percent;

    @JsonProperty("CashDiscount2Percent")
    private String cashDiscount2Percent;

    @JsonProperty("PurchasingOrganization")
    private String purchasingOrganization;

    @JsonProperty("PurchasingDocumentOrigin")
    private String purchasingDocumentOrigin;

    @JsonProperty("PurchasingGroup")
    private String purchasingGroup;

    @JsonProperty("PurchaseOrderDate")
    private String purchaseOrderDate;

    @JsonProperty("DocumentCurrency")
    private String documentCurrency;

    @JsonProperty("ExchangeRate")
    private String exchangeRate;

    @JsonProperty("ExchangeRateIsFixed")
    private Boolean exchangeRateIsFixed;

    @JsonProperty("ValidityStartDate")
    private String validityStartDate;

    @JsonProperty("ValidityEndDate")
    private String validityEndDate;

    @JsonProperty("SupplierQuotationExternalID")
    private String supplierQuotationExternalID;

    @JsonProperty("PurchasingCollectiveNumber")
    private String purchasingCollectiveNumber;

    @JsonProperty("SupplierRespSalesPersonName")
    private String supplierRespSalesPersonName;

    @JsonProperty("SupplierPhoneNumber")
    private String supplierPhoneNumber;

    @JsonProperty("SupplyingSupplier")
    private String supplyingSupplier;

    @JsonProperty("SupplyingPlant")
    private String supplyingPlant;

    @JsonProperty("IncotermsClassification")
    private String incotermsClassification;

    @JsonProperty("CorrespncExternalReference")
    private String correspncExternalReference;

    @JsonProperty("CorrespncInternalReference")
    private String correspncInternalReference;

    @JsonProperty("InvoicingParty")
    private String invoicingParty;

    @JsonProperty("ReleaseIsNotCompleted")
    private Boolean releaseIsNotCompleted;

    @JsonProperty("PurchasingCompletenessStatus")
    private Boolean purchasingCompletenessStatus;

    @JsonProperty("IncotermsVersion")
    private String incotermsVersion;

    @JsonProperty("IncotermsLocation1")
    private String incotermsLocation1;

    @JsonProperty("IncotermsLocation2")
    private String incotermsLocation2;

    @JsonProperty("ManualSupplierAddressID")
    private String manualSupplierAddressID;

    @JsonProperty("IsEndOfPurposeBlocked")
    private String isEndOfPurposeBlocked;

    @JsonProperty("AddressCityName")
    private String addressCityName;

    @JsonProperty("AddressFaxNumber")
    private String addressFaxNumber;

    @JsonProperty("AddressHouseNumber")
    private String addressHouseNumber;

    @JsonProperty("AddressName")
    private String addressName;

    @JsonProperty("AddressPostalCode")
    private String addressPostalCode;

    @JsonProperty("AddressStreetName")
    private String addressStreetName;

    @JsonProperty("AddressPhoneNumber")
    private String addressPhoneNumber;

    @JsonProperty("AddressRegion")
    private String addressRegion;

    @JsonProperty("AddressCountry")
    private String addressCountry;

    @JsonProperty("AddressCorrespondenceLanguage")
    private String addressCorrespondenceLanguage;

    @JsonProperty("PurgAggrgdProdCmplncSuplrSts")
    private String purgAggrgdProdCmplncSuplrSts;

    @JsonProperty("PurgAggrgdProdMarketabilitySts")
    private String purgAggrgdProdMarketabilitySts;

    @JsonProperty("PurgAggrgdSftyDataSheetStatus")
    private String purgAggrgdSftyDataSheetStatus;

    @JsonProperty("PurgProdCmplncTotDngrsGoodsSts")
    private String purgProdCmplncTotDngrsGoodsSts;

    @JsonProperty("to_PurchaseOrderItem")
    private NavigationResults<PurchaseOrderItemDTO> toPurchaseOrderItem;

    @JsonProperty("to_PurchaseOrderNote")
    private NavigationResults<PurchaseOrderNoteDTO> toPurchaseOrderNote;

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NavigationResults<T> {
        @JsonProperty("results")
        private List<T> results;
    }
}