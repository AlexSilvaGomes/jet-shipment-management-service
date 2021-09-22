
package com.jet.peoplemanagement.meli.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "reason",
    "status_code",
    "total_paid_amount",
    "operation_type",
    "transaction_amount",
    "date_approved",
    "collector",
    "coupon_id",
    "installments",
    "authorization_code",
    "taxes_amount",
    "id",
    "date_last_modified",
    "coupon_amount",
    "available_actions",
    "shipping_cost",
    "installment_amount",
    "date_created",
    "activation_uri",
    "overpaid_amount",
    "card_id",
    "status_detail",
    "issuer_id",
    "payment_method_id",
    "payment_type",
    "deferred_period",
    "atm_transfer_reference",
    "site_id",
    "payer_id",
    "order_id",
    "currency_id",
    "status",
    "transaction_order_id"
})
@Generated("jsonschema2pojo")
public class Payment {

    @JsonProperty("reason")
    public String reason;
    @JsonProperty("status_code")
    public Object statusCode;
    @JsonProperty("total_paid_amount")
    public Integer totalPaidAmount;
    @JsonProperty("operation_type")
    public String operationType;
    @JsonProperty("transaction_amount")
    public Integer transactionAmount;
    @JsonProperty("date_approved")
    public String dateApproved;
    @JsonProperty("collector")
    public Collector collector;
    @JsonProperty("coupon_id")
    public Object couponId;
    @JsonProperty("installments")
    public Integer installments;
    @JsonProperty("authorization_code")
    public String authorizationCode;
    @JsonProperty("taxes_amount")
    public Integer taxesAmount;
    @JsonProperty("id")
    public Long id;
    @JsonProperty("date_last_modified")
    public String dateLastModified;
    @JsonProperty("coupon_amount")
    public Integer couponAmount;
    @JsonProperty("available_actions")
    public List<String> availableActions = null;
    @JsonProperty("shipping_cost")
    public Integer shippingCost;
    @JsonProperty("installment_amount")
    public Integer installmentAmount;
    @JsonProperty("date_created")
    public String dateCreated;
    @JsonProperty("activation_uri")
    public Object activationUri;
    @JsonProperty("overpaid_amount")
    public Integer overpaidAmount;
    @JsonProperty("card_id")
    public Long cardId;
    @JsonProperty("status_detail")
    public String statusDetail;
    @JsonProperty("issuer_id")
    public String issuerId;
    @JsonProperty("payment_method_id")
    public String paymentMethodId;
    @JsonProperty("payment_type")
    public String paymentType;
    @JsonProperty("deferred_period")
    public Object deferredPeriod;
    @JsonProperty("atm_transfer_reference")
    public AtmTransferReference atmTransferReference;
    @JsonProperty("site_id")
    public String siteId;
    @JsonProperty("payer_id")
    public Integer payerId;
    @JsonProperty("order_id")
    public Long orderId;
    @JsonProperty("currency_id")
    public String currencyId;
    @JsonProperty("status")
    public String status;
    @JsonProperty("transaction_order_id")
    public Object transactionOrderId;

}
