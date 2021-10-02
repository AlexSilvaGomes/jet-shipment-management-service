package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Transactions implements Serializable {

	@JsonProperty("period")
	private String period;

	@JsonProperty("total")
	private Object total;

	@JsonProperty("completed")
	private Object completed;

	@JsonProperty("canceled")
	private Canceled canceled;

	@JsonProperty("unrated")
	private Unrated unrated;

	@JsonProperty("not_yet_rated")
	private NotYetRated notYetRated;

	public void setPeriod(String period){
		this.period = period;
	}

	public String getPeriod(){
		return period;
	}

	public void setTotal(Object total){
		this.total = total;
	}

	public Object getTotal(){
		return total;
	}

	public void setCompleted(Object completed){
		this.completed = completed;
	}

	public Object getCompleted(){
		return completed;
	}

	public void setCanceled(Canceled canceled){
		this.canceled = canceled;
	}

	public Canceled getCanceled(){
		return canceled;
	}

	public void setUnrated(Unrated unrated){
		this.unrated = unrated;
	}

	public Unrated getUnrated(){
		return unrated;
	}

	public void setNotYetRated(NotYetRated notYetRated){
		this.notYetRated = notYetRated;
	}

	public NotYetRated getNotYetRated(){
		return notYetRated;
	}
}