package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;
import java.util.List;

@Generated("com.robohorse.robopojogenerator")
public class Lista implements Serializable {

	@JsonProperty("allow")
	private boolean allow;

	@JsonProperty("codes")
	private List<Object> codes;

	@JsonProperty("immediate_payment")
	private ImmediatePayment immediatePayment;

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

	public void setImmediatePayment(ImmediatePayment immediatePayment){
		this.immediatePayment = immediatePayment;
	}

	public ImmediatePayment getImmediatePayment(){
		return immediatePayment;
	}
}