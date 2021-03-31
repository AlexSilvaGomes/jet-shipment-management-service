package com.jet.peoplemanagement.delivery;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeliveryRepository extends MongoRepository<DeliveryStatus, String> {
    List<DeliveryStatus> findByShipmentCode(String shipmentCode);
}
