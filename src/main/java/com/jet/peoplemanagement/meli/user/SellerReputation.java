package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.Generated;
import java.io.Serializable;

@Data
@Generated("com.robohorse.robopojogenerator")
public class SellerReputation implements Serializable {

	@JsonProperty("level_id")
	private Object levelId;

	@JsonProperty("power_seller_status")
	private Object powerSellerStatus;

	//@JsonProperty("transactions")
	//private Transactions transactions;

}