
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "amount",
    "id"
})
@Generated("jsonschema2pojo")
public class Coupon {

    @JsonProperty("amount")
    public Integer amount;
    @JsonProperty("id")
    public Object id;

}
