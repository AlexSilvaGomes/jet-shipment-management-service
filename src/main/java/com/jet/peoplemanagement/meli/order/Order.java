
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jet.peoplemanagement.shipment.Product;
import lombok.Data;

import javax.annotation.Generated;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "seller",
   // "payments",
    "fulfilled",
    "taxes",
    "order_request",
    "expiration_date",
    "feedback",
    "shipping",
    "date_closed",
    "id",
    "manufacturing_ending_date",
    "order_items",
    "date_last_updated",
    "last_updated",
    "comments",
    "pack_id",
    "coupon",
    "date_created",
    "pickup_id",
    "status_detail",
    "tags",
    "buyer",
    "total_amount",
    "paid_amount",
    "mediations",
    "currency_id",
    "status"
})
@Data
@Generated("jsonschema2pojo")
public class Order {

    @JsonProperty("seller")
    public Seller seller;
    //@JsonProperty("payments")
    //public List<Payment> payments = null;
    @JsonProperty("fulfilled")
    public Boolean fulfilled;
    @JsonProperty("taxes")
    public Taxes taxes;
    @JsonProperty("order_request")
    public OrderRequest orderRequest;
    @JsonProperty("expiration_date")
    public String expirationDate;
    @JsonProperty("feedback")
    public Feedback feedback;
    @JsonProperty("shipping")
    public Shipping shipping;
    @JsonProperty("date_closed")
    public String dateClosed;
    @JsonProperty("id")
    public String id;
    @JsonProperty("manufacturing_ending_date")
    public Object manufacturingEndingDate;
    @JsonProperty("order_items")
    public List<OrderItem> orderItems = null;
    @JsonProperty("date_last_updated")
    public String dateLastUpdated;
    @JsonProperty("last_updated")
    public String lastUpdated;
    @JsonProperty("comments")
    public Object comments;
    @JsonProperty("pack_id")
    public Object packId;
    @JsonProperty("coupon")
    public Coupon coupon;
    @JsonProperty("date_created")
    public String dateCreated;
    @JsonProperty("pickup_id")
    public Object pickupId;
    @JsonProperty("status_detail")
    public Object statusDetail;
    @JsonProperty("tags")
    public List<String> tags = null;
    @JsonProperty("buyer")
    public Buyer buyer;
    @JsonProperty("total_amount")
    public Integer totalAmount;
    @JsonProperty("paid_amount")
    public Integer paidAmount;
    @JsonProperty("mediations")
    public List<Object> mediations = null;
    @JsonProperty("currency_id")
    public String currencyId;
    @JsonProperty("status")
    public String status;

    public List<Product> toProducts(){
        return this.orderItems.stream().map(item -> {
          Product prod = new Product();
          prod.setId(item.getItem().getId());
          prod.setName(item.getItem().getTitle());
          prod.setQuantity(item.getQuantity());
          prod.setPrice(item.getUnitPrice());
          return prod;
        }).collect(Collectors.toList());
    }

}
