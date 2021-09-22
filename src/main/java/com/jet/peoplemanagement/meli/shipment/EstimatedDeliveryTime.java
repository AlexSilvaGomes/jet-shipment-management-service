
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "date",
    "unit",
    "offset",
    "time_frame",
    "pay_before",
    "shipping",
    "handling",
    "schedule"
})
@Generated("jsonschema2pojo")
public class EstimatedDeliveryTime {

    @JsonProperty("type")
    public String type;
    @JsonProperty("date")
    public String date;
    @JsonProperty("unit")
    public String unit;
    @JsonProperty("offset")
    public Offset offset;
    @JsonProperty("time_frame")
    public TimeFrame timeFrame;
    @JsonProperty("pay_before")
    public String payBefore;
    @JsonProperty("shipping")
    public Integer shipping;
    @JsonProperty("handling")
    public Integer handling;
    @JsonProperty("schedule")
    public Object schedule;

}
