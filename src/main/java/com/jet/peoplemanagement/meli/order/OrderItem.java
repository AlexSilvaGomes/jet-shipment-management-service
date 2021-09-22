
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "item",
    "quantity",
    "sale_fee",
    "listing_type_id",
    "unit_price",
    "full_unit_price",
    "currency_id",
    "manufacturing_days"
})
@Generated("jsonschema2pojo")
@Data
public class OrderItem {

    @JsonProperty("item")
    public Item item;
    @JsonProperty("quantity")
    public Integer quantity;
    @JsonProperty("sale_fee")
    public Integer saleFee;
    @JsonProperty("listing_type_id")
    public String listingTypeId;
    @JsonProperty("unit_price")
    public Integer unitPrice;
    @JsonProperty("full_unit_price")
    public Integer fullUnitPrice;
    @JsonProperty("currency_id")
    public String currencyId;
    @JsonProperty("manufacturing_days")
    public Object manufacturingDays;

}
