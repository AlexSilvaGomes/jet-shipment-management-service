
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date"
})
@Generated("jsonschema2pojo")
public class Buffering {

    @JsonProperty("date")
    public Object date;

}
