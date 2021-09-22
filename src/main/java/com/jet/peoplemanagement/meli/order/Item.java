
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "seller_custom_field",
    "condition",
    "global_price",
    "category_id",
    "variation_id",
    "variation_attributes",
    "seller_sku",
    "warranty",
    "id",
    "title"
})
@Generated("jsonschema2pojo")
@Data
public class Item {

    @JsonProperty("seller_custom_field")
    public Object sellerCustomField;
    @JsonProperty("condition")
    public String condition;
    @JsonProperty("global_price")
    public Object globalPrice;
    @JsonProperty("category_id")
    public String categoryId;
    @JsonProperty("variation_id")
    public Object variationId;
    @JsonProperty("variation_attributes")
    public List<Object> variationAttributes = null;
    @JsonProperty("seller_sku")
    public Object sellerSku;
    @JsonProperty("warranty")
    public String warranty;
    @JsonProperty("id")
    public String id;
    @JsonProperty("title")
    public String title;

}
