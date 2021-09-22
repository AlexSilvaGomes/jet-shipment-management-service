
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date",
    "shipping"
})
@Generated("jsonschema2pojo")
public class Offset {

    @JsonProperty("date")
    public String date;
    @JsonProperty("shipping")
    public Integer shipping;

}
