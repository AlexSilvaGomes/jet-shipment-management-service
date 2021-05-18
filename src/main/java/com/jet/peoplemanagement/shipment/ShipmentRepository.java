package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.Provider;
import com.jet.peoplemanagement.shipment.Shipment;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {
    Optional<Shipment> findByShipmentCode(String shipmentCode);
    List<Shipment>  findByShipmentCodeLikeAndClient(String shipmentCode, Client client);
    List<Shipment> findByShipmentCodeLike(String shipmentCode);
    List<Shipment> findByClient(Client client);
    List<Shipment> findByClientAndCreatedAtBetween(Client client, LocalDate periodEnd, LocalDate today);
    List<Shipment> findByCurrentProviderIdAndStatusAndCreatedAtBetween(String currentProviderId, DeliveryStatusEnum status, LocalDate periodEnd, LocalDate today);
    List<Shipment> findByCurrentProviderId(String currentProviderId);
    Page<Shipment> findAllByClient(Client client, Pageable page);
}
