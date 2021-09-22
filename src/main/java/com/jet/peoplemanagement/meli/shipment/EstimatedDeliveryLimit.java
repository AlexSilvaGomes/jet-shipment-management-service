
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date",
    "offset"
})
@Generated("jsonschema2pojo")
public class EstimatedDeliveryLimit {

    @JsonProperty("date")
    public Object date;
    @JsonProperty("offset")
    public Integer offset;

}
