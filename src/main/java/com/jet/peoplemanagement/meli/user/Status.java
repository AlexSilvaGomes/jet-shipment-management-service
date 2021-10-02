package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class Status implements Serializable {

	@JsonProperty("site_status")
	private String siteStatus;

	@JsonProperty("list")
	private Lista list;

	@JsonProperty("buy")
	private Buy buy;

	@JsonProperty("sell")
	private Sell sell;

	@JsonProperty("billing")
	private Billing billing;

	@JsonProperty("Mercado Pago_tc_accepted")
	private boolean mercadoPagoTcAccepted;

	@JsonProperty("Mercado Pago_account_type")
	private String mercadoPagoAccountType;

	@JsonProperty("Mercado Envios")
	private String mercadoEnvios;

	@JsonProperty("immediate_payment")
	private boolean immediatePayment;

	@JsonProperty("confirmed_email")
	private boolean confirmedEmail;

	@JsonProperty("user_type")
	private String userType;

	@JsonProperty("required_action")
	private String requiredAction;

	public void setSiteStatus(String siteStatus){
		this.siteStatus = siteStatus;
	}

	public String getSiteStatus(){
		return siteStatus;
	}

	public void setList(Lista list){
		this.list = list;
	}

	public Lista getList(){
		return list;
	}

	public void setBuy(Buy buy){
		this.buy = buy;
	}

	public Buy getBuy(){
		return buy;
	}

	public void setSell(Sell sell){
		this.sell = sell;
	}

	public Sell getSell(){
		return sell;
	}

	public void setBilling(Billing billing){
		this.billing = billing;
	}

	public Billing getBilling(){
		return billing;
	}

	public void setMercadoPagoTcAccepted(boolean mercadoPagoTcAccepted){
		this.mercadoPagoTcAccepted = mercadoPagoTcAccepted;
	}

	public boolean isMercadoPagoTcAccepted(){
		return mercadoPagoTcAccepted;
	}

	public void setMercadoPagoAccountType(String mercadoPagoAccountType){
		this.mercadoPagoAccountType = mercadoPagoAccountType;
	}

	public String getMercadoPagoAccountType(){
		return mercadoPagoAccountType;
	}

	public void setMercadoEnvios(String mercadoEnvios){
		this.mercadoEnvios = mercadoEnvios;
	}

	public String getMercadoEnvios(){
		return mercadoEnvios;
	}

	public void setImmediatePayment(boolean immediatePayment){
		this.immediatePayment = immediatePayment;
	}

	public boolean isImmediatePayment(){
		return immediatePayment;
	}

	public void setConfirmedEmail(boolean confirmedEmail){
		this.confirmedEmail = confirmedEmail;
	}

	public boolean isConfirmedEmail(){
		return confirmedEmail;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

	public String getUserType(){
		return userType;
	}

	public void setRequiredAction(String requiredAction){
		this.requiredAction = requiredAction;
	}

	public String getRequiredAction(){
		return requiredAction;
	}
}