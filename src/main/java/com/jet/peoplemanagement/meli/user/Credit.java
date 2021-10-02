package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Credit implements Serializable {

	@JsonProperty("consumed")
	private int consumed;

	@JsonProperty("credit_level_id")
	private String creditLevelId;

	public void setConsumed(int consumed){
		this.consumed = consumed;
	}

	public int getConsumed(){
		return consumed;
	}

	public void setCreditLevelId(String creditLevelId){
		this.creditLevelId = creditLevelId;
	}

	public String getCreditLevelId(){
		return creditLevelId;
	}
}