package com.jet.peoplemanagement.delivery;

import com.jet.peoplemanagement.model.BaseDocument;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.Provider;
import com.jet.peoplemanagement.model.Shipment;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "shipments")
public class Delivery extends BaseDocument {

    @DBRef
    @NotNull
    @Indexed
    private Shipment shipment;

    @NotBlank
    @Indexed(unique = true, name = "shipmentIndex")
    private String shipmentCode;

    @DBRef
    @NotNull
    @Indexed(unique = true, name = "provider")
    private Provider provider;

    @NotBlank
    private DeliveryStatus status;


}
