
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "change",
    "return"
})
@Generated("jsonschema2pojo")
public class OrderRequest {

    @JsonProperty("change")
    public Object change;
    @JsonProperty("return")
    public Object _return;

}
