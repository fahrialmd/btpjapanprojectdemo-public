package customer.btpjapanprojectdemo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleLineDTO {

    @JsonProperty("ScheduleLine")
    private String scheduleLine;

    @JsonProperty("ScheduleLineDeliveryDate")
    private String scheduleLineDeliveryDate;

    @JsonProperty("ScheduleLineOrderQuantity")
    private String scheduleLineOrderQuantity;

    @JsonProperty("to_SubcontractingComponent")
    private SubcontractingComponentWrapper toSubcontractingComponent;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SubcontractingComponentWrapper {
        @JsonProperty("results")
        private List<SubcontractingComponentDTO> results;
    }
}