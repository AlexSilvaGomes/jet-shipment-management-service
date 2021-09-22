
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "amount",
    "currency_id"
})
@Generated("jsonschema2pojo")
public class Taxes {

    @JsonProperty("amount")
    public Object amount;
    @JsonProperty("currency_id")
    public Object currencyId;

}
