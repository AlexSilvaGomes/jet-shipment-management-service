package com.jet.peoplemanagement.meli.user;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class BuyerReputation implements Serializable {

	@JsonProperty("canceled_transactions")
	private int canceledTransactions;

	@JsonProperty("transactions")
	private Transactions transactions;

	@JsonProperty("tags")
	private List<Object> tags;

	public void setCanceledTransactions(int canceledTransactions){
		this.canceledTransactions = canceledTransactions;
	}

	public int getCanceledTransactions(){
		return canceledTransactions;
	}

	public void setTransactions(Transactions transactions){
		this.transactions = transactions;
	}

	public Transactions getTransactions(){
		return transactions;
	}

	public void setTags(List<Object> tags){
		this.tags = tags;
	}

	public List<Object> getTags(){
		return tags;
	}
}