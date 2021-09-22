
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nickname",
    "id"
})
@Generated("jsonschema2pojo")
@Data
public class Buyer {

    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("id")
    private Integer id;

}
