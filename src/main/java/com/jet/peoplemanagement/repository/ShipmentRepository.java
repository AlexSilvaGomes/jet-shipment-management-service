package com.jet.peoplemanagement.repository;


import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {
}
