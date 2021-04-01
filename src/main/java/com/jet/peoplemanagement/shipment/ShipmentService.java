package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.delivery.ShipmentStatusService;
import com.jet.peoplemanagement.delivery.DeliveryStatusEnum;
import com.jet.peoplemanagement.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        Page<Shipment> pageable = shipmentRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));

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

    public void updateStatus(DeliveryStatusEnum status, String shipmentCode) {
        Shipment currentShip = findByShipmentCode(shipmentCode);
        currentShip.setStatus(status);//Atualizando com o último status
        save(currentShip);
    }
}
