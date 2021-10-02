package com.jet.peoplemanagement.meli.model;

import com.jet.peoplemanagement.shipment.Shipment;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class SelectedShipments  {

    public SelectedShipments(String clientId, List<Shipment> shipments) {
        this.shipments = shipments;
        this.clientId = clientId;
    }
    public SelectedShipments(){ }

    @NotBlank(message = "ClientId é obrigatório")
    private String clientId;

    private List<Shipment> shipments = new ArrayList<>();
}
