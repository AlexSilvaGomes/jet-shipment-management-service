package com.jet.peoplemanagement.meli.user;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class ImmediatePayment implements Serializable {

	@JsonProperty("required")
	private boolean required;

	@JsonProperty("reasons")
	private List<Object> reasons;

	public void setRequired(boolean required){
		this.required = required;
	}

	public boolean isRequired(){
		return required;
	}

	public void setReasons(List<Object> reasons){
		this.reasons = reasons;
	}

	public List<Object> getReasons(){
		return reasons;
	}
}