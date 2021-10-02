package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Unrated implements Serializable {

	@JsonProperty("total")
	private Object total;

	@JsonProperty("paid")
	private Object paid;

	public void setTotal(Object total){
		this.total = total;
	}

	public Object getTotal(){
		return total;
	}

	public void setPaid(Object paid){
		this.paid = paid;
	}

	public Object getPaid(){
		return paid;
	}
}