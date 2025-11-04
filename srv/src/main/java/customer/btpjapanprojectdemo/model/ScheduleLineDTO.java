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
@NoArgsConstructor // ← Jackson needs this for deserialization
@AllArgsConstructor // ← Builder needs this
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleLineDTO {

    @JsonProperty("PurchasingDocument")
    private String purchasingDocument;

    @JsonProperty("PurchasingDocumentItem")
    private String purchasingDocumentItem;

    @JsonProperty("ScheduleLine")
    private String scheduleLine;

    @JsonProperty("ScheduleLineDeliveryDate")
    private String scheduleLineDeliveryDate;

    @JsonProperty("ScheduleLineOrderQuantity")
    private String scheduleLineOrderQuantity;

    @JsonProperty("ScheduleLineDeliveryTime")
    private String scheduleLineDeliveryTime;

    @JsonProperty("SchedLineStscDeliveryDate")
    private String schedLineStscDeliveryDate;

    @JsonProperty("PurchaseRequisition")
    private String purchaseRequisition;

    @JsonProperty("PurchaseRequisitionItem")
    private String purchaseRequisitionItem;

    @JsonProperty("ScheduleLineCommittedQuantity")
    private String scheduleLineCommittedQuantity;

    @JsonProperty("PerformancePeriodStartDate")
    private String performancePeriodStartDate;

    @JsonProperty("PerformancePeriodEndDate")
    private String performancePeriodEndDate;

    @JsonProperty("to_SubcontractingComponent")
    private PurchaseOrderDTO.NavigationResults<SubcontractingComponentDTO> toSubcontractingComponent;

}