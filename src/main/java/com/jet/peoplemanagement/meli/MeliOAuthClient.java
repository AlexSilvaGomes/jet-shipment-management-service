package com.jet.peoplemanagement.meli;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MeliOAuthClient {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("user_id")
	private int userId;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private int expiresIn;

	private long calculatedExpiration;

	public void calculateExpiration() {
		this.calculatedExpiration = ( this.expiresIn * 1000 ) + System.currentTimeMillis();
	}
}