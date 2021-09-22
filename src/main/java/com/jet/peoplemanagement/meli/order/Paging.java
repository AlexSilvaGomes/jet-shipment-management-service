
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "offset",
    "limit"
})
@Generated("jsonschema2pojo")
public class Paging {

    @JsonProperty("total")
    public Integer total;
    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("limit")
    public Integer limit;

}
