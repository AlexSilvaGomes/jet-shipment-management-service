
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "mode",
    "created_by",
    "order_id",
    "order_cost",
    "base_cost",
    "site_id",
    "status",
    "substatus",
    "status_history",
    "substatus_history",
    "date_created",
    "last_updated",
    "tracking_number",
    "tracking_method",
    "service_id",
    "carrier_info",
    "sender_id",
    "sender_address",
    "receiver_id",
    "receiver_address",
    "shipping_items",
    "shipping_option",
    "comments",
    "date_first_printed",
    "market_place",
    "return_details",
    "tags",
    "type",
    "logistic_type",
    "application_id",
    "return_tracking_number",
    "cost_components"
})
@Generated("jsonschema2pojo")
@Data
public class ShipmentRoot {

    @JsonProperty("id")
    public Long id;
    @JsonProperty("mode")
    public String mode;
    @JsonProperty("created_by")
    public String createdBy;
    @JsonProperty("order_id")
    public Long orderId;
    @JsonProperty("order_cost")
    public Integer orderCost;
    @JsonProperty("base_cost")
    public Float baseCost;
    @JsonProperty("site_id")
    public String siteId;
    @JsonProperty("status")
    public String status;
    @JsonProperty("substatus")
    public Object substatus;
    @JsonProperty("status_history")
    public StatusHistory statusHistory;
    @JsonProperty("substatus_history")
    public List<Object> substatusHistory = null;
    @JsonProperty("date_created")
    public String dateCreated;
    @JsonProperty("last_updated")
    public String lastUpdated;
    @JsonProperty("tracking_number")
    public String trackingNumber;
    @JsonProperty("tracking_method")
    public String trackingMethod;
    @JsonProperty("service_id")
    public Integer serviceId;
    @JsonProperty("carrier_info")
    public Object carrierInfo;
    @JsonProperty("sender_id")
    public Integer senderId;
    @JsonProperty("sender_address")
    public SenderAddress senderAddress;
    @JsonProperty("receiver_id")
    public Integer receiverId;
    @JsonProperty("receiver_address")
    public ReceiverAddress receiverAddress;
    @JsonProperty("shipping_items")
    public List<ShippingItem> shippingItems = null;
    @JsonProperty("shipping_option")
    public ShippingOption shippingOption;
    @JsonProperty("comments")
    public Object comments;
    @JsonProperty("date_first_printed")
    public String dateFirstPrinted;
    @JsonProperty("market_place")
    public String marketPlace;
    @JsonProperty("return_details")
    public Object returnDetails;
    @JsonProperty("tags")
    public List<Object> tags = null;
    @JsonProperty("type")
    public String type;
    @JsonProperty("logistic_type")
    public String logisticType;
    @JsonProperty("application_id")
    public Object applicationId;
    @JsonProperty("return_tracking_number")
    public Object returnTrackingNumber;
    @JsonProperty("cost_components")
    public CostComponents costComponents;

}
