package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.model.BaseDocument;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import com.jet.peoplemanagement.shipmentStatus.ShipmentStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Document(collection = "shipments")
public class Shipment extends BaseDocument {

    @DBRef
    @NotNull
    @Indexed
    private Client client;

    @Indexed
    private String currentProviderId;
    private String currentProviderName;

    private List<Product> products;
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

    private DeliveryStatusEnum status = DeliveryStatusEnum.POSTADO;

    private List<ShipmentStatus> shipmentsStatus;

    private Double price;


}
