package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepositoryCustom {

    public Page<Shipment> getShipmentsByParamsPageable(ShipmentFilter filter);
    public List<Shipment> getShipmentsByParams(ShipmentFilter filter);
    public Optional<Client> queroByEmail(String cnpj);

}
