
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date_cancelled",
    "date_delivered",
    "date_first_visit",
    "date_handling",
    "date_not_delivered",
    "date_ready_to_ship",
    "date_shipped",
    "date_returned"
})
@Generated("jsonschema2pojo")
public class StatusHistory {

    @JsonProperty("date_cancelled")
    public Object dateCancelled;
    @JsonProperty("date_delivered")
    public String dateDelivered;
    @JsonProperty("date_first_visit")
    public String dateFirstVisit;
    @JsonProperty("date_handling")
    public String dateHandling;
    @JsonProperty("date_not_delivered")
    public Object dateNotDelivered;
    @JsonProperty("date_ready_to_ship")
    public String dateReadyToShip;
    @JsonProperty("date_shipped")
    public String dateShipped;
    @JsonProperty("date_returned")
    public Object dateReturned;

}
