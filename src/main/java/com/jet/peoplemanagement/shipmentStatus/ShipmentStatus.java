package com.jet.peoplemanagement.shipmentStatus;

import com.jet.peoplemanagement.model.BaseDocument;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "deliveries")
public class ShipmentStatus extends BaseDocument {

    @NotBlank
    @Indexed(unique = false, name = "shipmentIndex")
    private String shipmentCode;

    @NotBlank
    @Indexed(unique = false, name = "cpfIndex")
    private String statusResponsibleCpf;

    @NotBlank
    private String statusResponsibleName;

    @NotBlank
    private String statusResponsibleId;

    //private String providerConferenceCpf;

    @NotNull
    private DeliveryStatusEnum status;
}
