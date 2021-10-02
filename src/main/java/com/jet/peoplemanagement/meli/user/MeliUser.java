package com.jet.peoplemanagement.meli.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class MeliUser implements Serializable {

	@JsonProperty("id")
	private int id;

	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("registration_date")
	private String registrationDate;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("country_id")
	private String countryId;

	@JsonProperty("email")
	private String email;

	@JsonProperty("identification")
	private Identification identification;

	@JsonProperty("address")
	private Address address;

	@JsonProperty("phone")
	private Phone phone;

	@JsonProperty("alternative_phone")
	private AlternativePhone alternativePhone;

	@JsonProperty("user_type")
	private String userType;

	@JsonProperty("tags")
	private List<String> tags;

	@JsonProperty("points")
	private int points;

	@JsonProperty("site_id")
	private String siteId;

	@JsonProperty("permalink")
	private String permalink;

	@JsonProperty("shipping_modes")
	private List<String> shippingModes;

	@JsonProperty("seller_experience")
	private String sellerExperience;

	@JsonProperty("seller_reputation")
	private SellerReputation sellerReputation;

	@JsonProperty("buyer_reputation")
	private BuyerReputation buyerReputation;

	@JsonProperty("status")
	private Status status;

	@JsonProperty("credit")
	private Credit credit;
}