package com.jet.peoplemanagement.delivery;

import com.jet.peoplemanagement.exception.EntityNotFoundException;
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

@Service
@Slf4j
public class DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    ShipmentService shipmentService;

    public Page<DeliveryStatus> findAll(Integer pageNumber, Integer pageSize) {
        Page<DeliveryStatus> pageable = deliveryRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));

        if (!pageable.hasContent()) throw new EntityNotFoundException(DeliveryStatus.class);

        return pageable;
    }

    public DeliveryStatus findById(String id) {
        Optional<DeliveryStatus> deliveryData = deliveryRepository.findById(id);

        if (deliveryData.isPresent()) return deliveryData.get();

        else throw new EntityNotFoundException(DeliveryStatus.class, "id", id);
    }

    public DeliveryStatus save(DeliveryStatus delivery) {
        delivery.setCreatedAt(LocalDateTime.now());
        DeliveryStatus deliveryResult = deliveryRepository.save(delivery);
        shipmentService.updateStatus(delivery.getStatus(), delivery.getShipmentCode());
        return deliveryResult;
    }

    /**
     * Vai salvar sem atualizar status no shipment
     * @param delivery
     * @return
     */
    public DeliveryStatus justSave(DeliveryStatus delivery) {
        delivery.setCreatedAt(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }

    public DeliveryStatus update(String id, DeliveryStatus updatedDelivery) {
        Optional<DeliveryStatus> deliveryData = deliveryRepository.findById(id);

        if (deliveryData.isPresent()) {
            DeliveryStatus dbDelivery = deliveryData.get();
            String ignored[] = {"id", "createdAt"};
            BeanUtils.copyProperties(updatedDelivery, dbDelivery, ignored);
            return deliveryRepository.save(dbDelivery);
        } else throw new EntityNotFoundException(DeliveryStatus.class, "id", id);
    }

    public void deleteById(String id) {
        DeliveryStatus document = findById(id);
        log.info("Deleting delivery with id {}", id);
        deliveryRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        deliveryRepository.deleteAll();
    }

    public List<DeliveryStatus> findByShipmentCode(String shipmentCode) {
        List<DeliveryStatus> deliveryData = deliveryRepository.findByShipmentCode(shipmentCode);

        if (Collections.isEmpty(deliveryData))
            throw new EntityNotFoundException(DeliveryStatus.class, "shipmentCode", shipmentCode);
        else return deliveryData;
    }
}
