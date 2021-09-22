
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "origin"
})
@Generated("jsonschema2pojo")
public class DimensionsSource {

    @JsonProperty("id")
    public String id;
    @JsonProperty("origin")
    public String origin;

}
