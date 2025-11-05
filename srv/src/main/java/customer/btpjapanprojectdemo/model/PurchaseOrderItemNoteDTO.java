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
public class PurchaseOrderItemNoteDTO {

    @JsonProperty("PurchaseOrder")
    private String purchaseOrder;

    @JsonProperty("PurchaseOrderItem")
    private String purchaseOrderItem;

    @JsonProperty("TextObjectType")
    private String textObjectType;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("PlainLongText")
    private String plainLongText;

}
