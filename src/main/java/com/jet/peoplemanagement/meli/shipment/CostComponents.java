
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "special_discount",
    "loyal_discount",
    "compensation",
    "gap_discount",
    "ratio"
})
@Generated("jsonschema2pojo")
public class CostComponents {

    @JsonProperty("special_discount")
    public Integer specialDiscount;
    @JsonProperty("loyal_discount")
    public Integer loyalDiscount;
    @JsonProperty("compensation")
    public Integer compensation;
    @JsonProperty("gap_discount")
    public Integer gapDiscount;
    @JsonProperty("ratio")
    public Float ratio;

}
