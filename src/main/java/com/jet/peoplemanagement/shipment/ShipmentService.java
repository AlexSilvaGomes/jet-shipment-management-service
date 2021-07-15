package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.invoice.Invoice;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipmentStatus.ShipmentStatus;
import com.jet.peoplemanagement.shipmentStatus.ShipmentStatusService;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import com.jet.peoplemanagement.exception.EntityNotFoundException;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class ShipmentService {

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    ShipmentStatusService deliveryService;

    public Page<Shipment> findAll(Integer pageNumber, Integer pageSize) {
        Page<Shipment> pageable = shipmentRepository.findAll(
                PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize,
                        Sort.Direction.DESC, "updatedAt"));

        if (!pageable.hasContent()) throw new EntityNotFoundException(Shipment.class);

        return pageable;
    }

    public Page<Shipment> findAllByClient(Client client, Integer pageNumber, Integer pageSize) {
        Page<Shipment> pageable = shipmentRepository.findAllByClient(client,
                PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize,
                        Sort.Direction.DESC, "updatedAt"));

        if (!pageable.hasContent()) throw new EntityNotFoundException(Shipment.class);

        return pageable;
    }

    public Shipment findById(String id) {
        Optional<Shipment> shipmentData = shipmentRepository.findById(id);

        if (shipmentData.isPresent()) {
            Shipment shipmentResult = shipmentData.get();
            shipmentResult.setShipmentsStatus(deliveryService.findByShipmentCode(shipmentResult.getShipmentCode()));
            return shipmentResult;
        } else throw new EntityNotFoundException(Shipment.class, "id", id);
    }

    public Shipment save(Shipment shipment) {
        shipment.setCreatedAt(LocalDateTime.now());
        return shipmentRepository.save(shipment);
    }

    public Shipment update(String id, Shipment updatedShipment) {
        Optional<Shipment> shipmentData = shipmentRepository.findById(id);

        if (shipmentData.isPresent()) {
            Shipment dbShipment = shipmentData.get();
            String ignored[] = {"id", "createdAt"};
            BeanUtils.copyProperties(updatedShipment, dbShipment, ignored);
            return shipmentRepository.save(dbShipment);
        } else throw new EntityNotFoundException(Shipment.class, "id", id);
    }

    public void deleteById(String id) {
        Shipment document = findById(id);
        log.info("Deleting shipment with id {}", id);
        shipmentRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        shipmentRepository.deleteAll();
    }

    public Shipment findByShipmentCode(String shipmentCode) {
        Optional<Shipment> shipmentData = shipmentRepository.findByShipmentCode(shipmentCode);

        if (shipmentData.isPresent()) {
            return shipmentData.get();
        } else throw new EntityNotFoundException(Shipment.class, "shipmentCode", shipmentCode);
    }

    public Page<Shipment> findByClientStatusAndPeriodPageable(ShipmentFilter filter) {
        return shipmentRepository.getShipmentsByParamsPageable(filter);
    }

    public List<Shipment> findByClientStatusAndPeriod(ShipmentFilter filter) {
        return shipmentRepository.getShipmentsByParams(filter);
    }

    public List<Shipment> findByProviderAndStatus(String providerId, DeliveryStatusEnum status) {
        LocalDate today = LocalDate.now();

        List<Shipment> shipments = shipmentRepository.
                findByCurrentProviderIdAndStatusAndCreatedAtBetween(providerId, status, today, today.plusDays(1));
        //List<Shipment> shipments = shipmentRepository.findByProvider(provider);

        return shipments;
    }

    public Page<Shipment> findByShipmentCodeLike(String shipmentCode) {
        List<Shipment> shipmentData = shipmentRepository.findByShipmentCodeLike(shipmentCode);

        if (!Collections.isEmpty(shipmentData)) {
            Page page = new PageImpl(shipmentData, PageRequest.of(0, shipmentData.size()), shipmentData.size());
            return page;
        } else throw new EntityNotFoundException(Shipment.class, "shipmentCode", shipmentCode);
    }

    public Page<Shipment> findByShipmentCodeLikeAndClient(String shipmentCode, Client client) {
        List<Shipment> shipmentData = shipmentRepository.findByShipmentCodeLikeAndClient(shipmentCode, client);

        if (!Collections.isEmpty(shipmentData)) {
            Page page = new PageImpl(shipmentData, PageRequest.of(0, shipmentData.size()), shipmentData.size());
            return page;
        } else throw new EntityNotFoundException(Shipment.class, "shipmentCode", shipmentCode);
    }

    public void updateStatus(ShipmentStatus status) {
        Shipment currentShip = findByShipmentCode(status.getShipmentCode());
        currentShip.setStatus(status.getStatus());//Atualizando com o último status
        currentShip.setCurrentProviderId(status.getStatusResponsibleId());
        save(currentShip);
    }

    public List<Shipment> findByClientAndOptionalLastInvoice(Client client, Optional<Invoice> lastInvoice) {

        LocalDate today = LocalDate.now();
        List<Shipment> shipments;

        if (lastInvoice.isPresent()) {
            shipments = shipmentRepository.findByClientAndCreatedAtBetween(client, lastInvoice.get().getPeriodEnd(), today.plusDays(1));
        } else {
            shipments = shipmentRepository.findByClient(client);
        }

        return shipments;
    }

}
