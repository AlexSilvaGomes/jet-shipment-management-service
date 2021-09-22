
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to"
})
@Generated("jsonschema2pojo")
public class TimeFrame {

    @JsonProperty("from")
    public Object from;
    @JsonProperty("to")
    public Object to;

}
