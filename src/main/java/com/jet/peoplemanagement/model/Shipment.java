package com.jet.peoplemanagement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "shipments")
public class Shipment extends BaseDocument {

    @DBRef
    @NotNull
    @Indexed
    private Client client;

    private String productName;
    private String productDesc;
    private String sku;

    @NotBlank
    @Indexed(unique = true, name = "shipmentIndex")
    private String shipmentCode;

    @NotBlank
    @Indexed(unique = true, name = "saleIndex")
    private String saleCode;

    //TODO mapear enum para zona (ou TAbela)

    private String shipType;

    @NotBlank
    private String zone;
    private String receiverNeighbor;

    private String receiverName;
    private String receiverNickName;

    private String receiverAddress;
    private String receiverAddressComp;
    private String receiverCity;
    private String receiverCep;

}
