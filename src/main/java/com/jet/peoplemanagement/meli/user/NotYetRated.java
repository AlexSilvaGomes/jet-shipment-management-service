package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class NotYetRated implements Serializable {

	@JsonProperty("total")
	private Object total;

	@JsonProperty("paid")
	private Object paid;

	@JsonProperty("units")
	private Object units;

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

	public void setUnits(Object units){
		this.units = units;
	}

	public Object getUnits(){
		return units;
	}
}