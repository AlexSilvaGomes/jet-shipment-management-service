package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Ratings implements Serializable {

	@JsonProperty("positive")
	private int positive;

	@JsonProperty("negative")
	private int negative;

	@JsonProperty("neutral")
	private int neutral;

	public void setPositive(int positive){
		this.positive = positive;
	}

	public int getPositive(){
		return positive;
	}

	public void setNegative(int negative){
		this.negative = negative;
	}

	public int getNegative(){
		return negative;
	}

	public void setNeutral(int neutral){
		this.neutral = neutral;
	}

	public int getNeutral(){
		return neutral;
	}
}