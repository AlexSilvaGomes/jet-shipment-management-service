
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transaction_id",
    "company_id"
})
@Generated("jsonschema2pojo")
public class AtmTransferReference {

    @JsonProperty("transaction_id")
    public Object transactionId;
    @JsonProperty("company_id")
    public Object companyId;

}
