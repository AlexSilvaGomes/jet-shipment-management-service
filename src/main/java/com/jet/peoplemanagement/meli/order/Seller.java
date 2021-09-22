
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nickname",
    "id"
})
@Generated("jsonschema2pojo")
public class Seller {

    @JsonProperty("nickname")
    public String nickname;
    @JsonProperty("id")
    public Integer id;

}
