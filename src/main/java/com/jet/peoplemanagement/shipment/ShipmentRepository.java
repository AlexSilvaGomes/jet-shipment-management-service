package com.jet.peoplemanagement.shipment;


import com.jet.peoplemanagement.shipment.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {
    Optional<Shipment> findByShipmentCode(String shipmentCode);
}
