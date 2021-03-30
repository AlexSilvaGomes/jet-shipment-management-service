package com.jet.peoplemanagement.shipment;


import com.jet.peoplemanagement.shipment.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {
}
