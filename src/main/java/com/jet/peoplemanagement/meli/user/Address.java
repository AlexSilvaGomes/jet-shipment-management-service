package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Address implements Serializable {

	@JsonProperty("state")
	private String state;

	@JsonProperty("city")
	private String city;

	@JsonProperty("address")
	private String address;

	@JsonProperty("zip_code")
	private String zipCode;

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}

	public String getZipCode(){
		return zipCode;
	}
}