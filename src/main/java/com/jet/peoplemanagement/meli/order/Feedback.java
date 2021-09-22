
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sale",
    "purchase"
})
@Generated("jsonschema2pojo")
public class Feedback {

    @JsonProperty("sale")
    public Object sale;
    @JsonProperty("purchase")
    public Object purchase;

}
