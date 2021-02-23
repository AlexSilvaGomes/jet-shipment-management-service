package com.jet.peoplemanagement.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "shipments")
public class Shipment extends BaseDocument {

    @DBRef
    @NotNull
    @Indexed
    private Client client;

    @NotBlank
    @Indexed(unique = true, name = "shipmentIndex")
    private String shipmentCode;

    @NotBlank
    @Indexed(unique = true, name = "saleIndex")
    private String saleCode;

    //TODO mapear enum para zona (ou TAbela)
    @NotBlank
    private String zone;

    @Indexed(unique = true, name = "deliveryPersonIndex")
    private String deliveryPersonId;

    @Indexed(unique = true, name = "collectorPersonIndex")
    private String collectorPersonId;

    private String receiverName;
    private String receiverNeighborhood;
    private String receiverAddress;
    private String receiverAddressComp;
    private String receiverCep;
    private String receiverCity;

    @NotBlank
    private String status;
}
