package com.jet.peoplemanagement.delivery;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShipmentStatusRepository extends MongoRepository<ShipmentStatus, String> {
    List<ShipmentStatus> findByShipmentCode(String shipmentCode);
}
