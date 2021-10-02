package com.jet.peoplemanagement.meli.user;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Billing implements Serializable {

	@JsonProperty("allow")
	private boolean allow;

	@JsonProperty("codes")
	private List<Object> codes;

	public void setAllow(boolean allow){
		this.allow = allow;
	}

	public boolean isAllow(){
		return allow;
	}

	public void setCodes(List<Object> codes){
		this.codes = codes;
	}

	public List<Object> getCodes(){
		return codes;
	}
}