
package com.jet.peoplemanagement.meli.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "address_line",
    "street_name",
    "street_number",
    "comment",
    "zip_code",
    "city",
    "state",
    "country",
    "neighborhood",
    "municipality",
    "agency",
    "types",
    "latitude",
    "longitude",
    "geolocation_type",
    "geolocation_last_updated",
    "geolocation_source",
    "delivery_preference",
    "receiver_name",
    "receiver_phone"
})
@Generated("jsonschema2pojo")
public class ReceiverAddress {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("address_line")
    public String addressLine;
    @JsonProperty("street_name")
    public String streetName;
    @JsonProperty("street_number")
    public String streetNumber;
    @JsonProperty("comment")
    public String comment;
    @JsonProperty("zip_code")
    public String zipCode;
    @JsonProperty("city")
    public City__1 city;
    @JsonProperty("state")
    public State__1 state;
    @JsonProperty("country")
    public Country__1 country;
    @JsonProperty("neighborhood")
    public Neighborhood__1 neighborhood;
    @JsonProperty("municipality")
    public Municipality__1 municipality;
    @JsonProperty("agency")
    public Object agency;
    @JsonProperty("types")
    public List<String> types = null;
    @JsonProperty("latitude")
    public Float latitude;
    @JsonProperty("longitude")
    public Float longitude;
    @JsonProperty("geolocation_type")
    public String geolocationType;
    @JsonProperty("geolocation_last_updated")
    public String geolocationLastUpdated;
    @JsonProperty("geolocation_source")
    public String geolocationSource;
    @JsonProperty("delivery_preference")
    public String deliveryPreference;
    @JsonProperty("receiver_name")
    public String receiverName;
    @JsonProperty("receiver_phone")
    public String receiverPhone;

}
