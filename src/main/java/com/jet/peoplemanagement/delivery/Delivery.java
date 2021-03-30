package com.jet.peoplemanagement.delivery;

import com.jet.peoplemanagement.model.BaseDocument;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "deliveries")
public class Delivery extends BaseDocument {


    @NotBlank
    @Indexed(unique = false, name = "shipmentIndex")
    private String shipmentCode;

    @NotBlank
    @Indexed(unique = false, name = "cpfIndex")
    private String providerDeliveryCpf;

    @NotBlank
    private String providerDeliveryName;

    @Indexed(unique = true, name = "cpfIndex")
    private String providerConferenceCpf;

    @NotNull
    private DeliveryStatus status;
}
