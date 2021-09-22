
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "query",
    "results",
    "sort",
    "available_sorts",
    "filters",
    "paging",
    "display"
})
@Generated("jsonschema2pojo")
public class OrderRoot {

    @JsonProperty("query")
    public String query;
    @JsonProperty("results")
    public List<Order> orders = null;
    @JsonProperty("sort")
    public Sort sort;
    @JsonProperty("available_sorts")
    public List<AvailableSort> availableSorts = null;
    @JsonProperty("filters")
    public List<Object> filters = null;
    @JsonProperty("paging")
    public Paging paging;
    @JsonProperty("display")
    public String display;

}
