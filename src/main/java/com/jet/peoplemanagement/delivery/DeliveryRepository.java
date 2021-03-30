package com.jet.peoplemanagement.delivery;


import com.jet.peoplemanagement.shipment.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    List<Delivery> findByShipmentCode(String shipmentCode);
}
