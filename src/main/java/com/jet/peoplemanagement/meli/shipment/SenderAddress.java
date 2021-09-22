
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
    "geolocation_source"
})
@Generated("jsonschema2pojo")
public class SenderAddress {

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
    public City city;
    @JsonProperty("state")
    public State state;
    @JsonProperty("country")
    public Country country;
    @JsonProperty("neighborhood")
    public Neighborhood neighborhood;
    @JsonProperty("municipality")
    public Municipality municipality;
    @JsonProperty("agency")
    public Object agency;
    @JsonProperty("types")
    public List<String> types = null;
    @JsonProperty("latitude")
    public Integer latitude;
    @JsonProperty("longitude")
    public Integer longitude;
    @JsonProperty("geolocation_type")
    public String geolocationType;
    @JsonProperty("geolocation_last_updated")
    public String geolocationLastUpdated;
    @JsonProperty("geolocation_source")
    public String geolocationSource;

}
