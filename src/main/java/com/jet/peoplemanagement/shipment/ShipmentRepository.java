package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipment.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {
    Optional<Shipment> findByShipmentCode(String shipmentCode);
    List<Shipment> findByShipmentCodeLike(String shipmentCode);
    List<Shipment> findByClient(Client client);
    List<Shipment> findByClientAndCreatedAtBetween(Client client, LocalDate periodEnd, LocalDate today);
}
