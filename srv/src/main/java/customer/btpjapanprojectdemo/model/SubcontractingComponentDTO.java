package customer.btpjapanprojectdemo.model;

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
public class SubcontractingComponentDTO {

    @JsonProperty("PurchaseOrder")
    private String purchaseOrder;

    @JsonProperty("PurchaseOrderItem")
    private String purchaseOrderItem;

    @JsonProperty("ScheduleLine")
    private String scheduleLine;

    @JsonProperty("ReservationItem")
    private String reservationItem;

    @JsonProperty("RecordType")
    private String recordType;

    @JsonProperty("Material")
    private String material;

    @JsonProperty("BOMItemDescription")
    private String bomItemDescription;

    @JsonProperty("RequiredQuantity")
    private String requiredQuantity;

    @JsonProperty("BaseUnit")
    private String baseUnit;

    @JsonProperty("RequirementDate")
    private String requirementDate;

    @JsonProperty("QuantityInEntryUnit")
    private String quantityInEntryUnit;

    @JsonProperty("EntryUnit")
    private String entryUnit;

    @JsonProperty("WithdrawnQuantity")
    private String withdrawnQuantity;

    @JsonProperty("Plant")
    private String plant;

    @JsonProperty("Batch")
    private String batch;
}