
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "description",
    "quantity",
    "dimensions",
    "dimensions_source"
})
@Generated("jsonschema2pojo")
public class ShippingItem {

    @JsonProperty("id")
    public String id;
    @JsonProperty("description")
    public String description;
    @JsonProperty("quantity")
    public Integer quantity;
    @JsonProperty("dimensions")
    public String dimensions;
    @JsonProperty("dimensions_source")
    public DimensionsSource dimensionsSource;

}
