package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Identification implements Serializable {

	@JsonProperty("type")
	private String type;

	@JsonProperty("number")
	private String number;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setNumber(String number){
		this.number = number;
	}

	public String getNumber(){
		return number;
	}
}