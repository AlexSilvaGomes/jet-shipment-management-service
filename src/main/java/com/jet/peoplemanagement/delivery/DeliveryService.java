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

    public Page<Delivery> findAll(Integer pageNumber, Integer pageSize) {
        Page<Delivery> pageable = deliveryRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));

        if (!pageable.hasContent()) throw new EntityNotFoundException(Delivery.class);

        return pageable;
    }

    public Delivery findById(String id) {
        Optional<Delivery> deliveryData = deliveryRepository.findById(id);

        if (deliveryData.isPresent()) return deliveryData.get();

        else throw new EntityNotFoundException(Delivery.class, "id", id);
    }

    public Delivery save(Delivery delivery) {
        delivery.setCreatedAt(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }

    public Delivery update(String id, Delivery updatedDelivery) {
        Optional<Delivery> deliveryData = deliveryRepository.findById(id);

        if (deliveryData.isPresent()) {
            Delivery dbDelivery = deliveryData.get();
            String ignored [] = {"id", "createdAt"};
            BeanUtils.copyProperties(updatedDelivery, dbDelivery, ignored);
            return deliveryRepository.save(dbDelivery);
        } else throw new EntityNotFoundException(Delivery.class, "id", id);
    }

    public void deleteById(String id) {
        Delivery document = findById(id);
        log.info("Deleting delivery with id {}", id);
        deliveryRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        deliveryRepository.deleteAll();
    }

    public List<Delivery> findByShipmentCode(String shipmentCode) {
        List<Delivery> deliveryData = deliveryRepository.findByShipmentCode(shipmentCode);

        if (Collections.isEmpty(deliveryData)) throw new EntityNotFoundException(Delivery.class, "shipmentCode", shipmentCode);
        else return deliveryData;
    }
}
