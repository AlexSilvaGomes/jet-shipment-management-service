package com.jet.peoplemanagement.shipmentStatus;

import com.jet.peoplemanagement.exception.EntityNotFoundException;
import com.jet.peoplemanagement.shipment.Shipment;
import com.jet.peoplemanagement.shipment.ShipmentService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class ShipmentStatusService {

    @Autowired
    ShipmentStatusRepository deliveryRepository;

    @Autowired
    ShipmentService shipmentService;

    public static final String SYSTEM_ADMIN = "Administrador do sistema";

    public Page<ShipmentStatus> findAll(Integer pageNumber, Integer pageSize) {
        Page<ShipmentStatus> pageable = deliveryRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));

        if (!pageable.hasContent()) throw new EntityNotFoundException(ShipmentStatus.class);

        return pageable;
    }

    public ShipmentStatus findById(String id) {
        Optional<ShipmentStatus> deliveryData = deliveryRepository.findById(id);

        if (deliveryData.isPresent()) return deliveryData.get();

        else throw new EntityNotFoundException(ShipmentStatus.class, "id", id);
    }

    public ShipmentStatus save(ShipmentStatus status) {
        status.setCreatedAt(LocalDateTime.now());
        ShipmentStatus deliveryResult = deliveryRepository.save(status);
        shipmentService.updateStatus(status);
        return deliveryResult;
    }

    /**
     * Vai salvar sem atualizar status no shipment
     * @param delivery
     * @return
     */
    public ShipmentStatus justSave(ShipmentStatus delivery) {
        delivery.setCreatedAt(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }

    public void createShipmentStatus(Shipment shipSaved) {
        ShipmentStatus delivery = new ShipmentStatus();
        delivery.setShipmentCode(shipSaved.getShipmentCode());
        delivery.setStatus( DeliveryStatusEnum.POSTADO);
        String name = nonNull(shipSaved.getClient()) ? shipSaved.getClient().getName() : SYSTEM_ADMIN;
        delivery.setStatusResponsibleName(name);
        try {
            justSave(delivery);
        } catch (Exception e) {
            shipmentService.deleteById(shipSaved.getId());
            throw e;
        }
    }

    public ShipmentStatus update(String id, ShipmentStatus updatedDelivery) {
        Optional<ShipmentStatus> deliveryData = deliveryRepository.findById(id);

        if (deliveryData.isPresent()) {
            ShipmentStatus dbDelivery = deliveryData.get();
            String ignored[] = {"id", "createdAt"};
            BeanUtils.copyProperties(updatedDelivery, dbDelivery, ignored);
            return deliveryRepository.save(dbDelivery);
        } else throw new EntityNotFoundException(ShipmentStatus.class, "id", id);
    }

    public void deleteById(String id) {
        ShipmentStatus document = findById(id);
        log.info("Deleting delivery with id {}", id);
        deliveryRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        deliveryRepository.deleteAll();
    }

    public List<ShipmentStatus> findByShipmentCode(String shipmentCode) {
        List<ShipmentStatus> deliveryData = deliveryRepository.findByShipmentCode(shipmentCode);

        if (Collections.isEmpty(deliveryData))
            throw new EntityNotFoundException(ShipmentStatus.class, "shipmentCode", shipmentCode);
        else return deliveryData;
    }
}
