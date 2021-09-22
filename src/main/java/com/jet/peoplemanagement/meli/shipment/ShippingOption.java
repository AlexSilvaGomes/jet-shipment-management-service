
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "shipping_method_id",
    "name",
    "currency_id",
    "list_cost",
    "cost",
    "delivery_type",
    "estimated_schedule_limit",
    "buffering",
    "estimated_delivery_time",
    "estimated_delivery_limit",
    "estimated_delivery_final",
    "estimated_delivery_extended",
    "estimated_handling_limit"
})
@Generated("jsonschema2pojo")
public class ShippingOption {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("shipping_method_id")
    public Integer shippingMethodId;
    @JsonProperty("name")
    public String name;
    @JsonProperty("currency_id")
    public String currencyId;
    @JsonProperty("list_cost")
    public Float listCost;
    @JsonProperty("cost")
    public Integer cost;
    @JsonProperty("delivery_type")
    public String deliveryType;
    @JsonProperty("estimated_schedule_limit")
    public EstimatedScheduleLimit estimatedScheduleLimit;
    @JsonProperty("buffering")
    public Buffering buffering;
    @JsonProperty("estimated_delivery_time")
    public EstimatedDeliveryTime estimatedDeliveryTime;
    @JsonProperty("estimated_delivery_limit")
    public EstimatedDeliveryLimit estimatedDeliveryLimit;
    @JsonProperty("estimated_delivery_final")
    public EstimatedDeliveryFinal estimatedDeliveryFinal;
    @JsonProperty("estimated_delivery_extended")
    public EstimatedDeliveryExtended estimatedDeliveryExtended;
    @JsonProperty("estimated_handling_limit")
    public EstimatedHandlingLimit estimatedHandlingLimit;

}
