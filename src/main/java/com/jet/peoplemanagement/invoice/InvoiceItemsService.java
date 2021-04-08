package com.jet.peoplemanagement.invoice;

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
public class InvoiceItemsService {

    @Autowired
    InvoiceItemsRepository itemsRepository;

    @Autowired
    ShipmentService shipmentService;

    public Page<InvoiceItems> findAll(Integer pageNumber, Integer pageSize) {
        Page<InvoiceItems> pageable = itemsRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));
        if (!pageable.hasContent()) throw new EntityNotFoundException(InvoiceItems.class);
        return pageable;
    }

    public InvoiceItems findById(String id) {
        Optional<InvoiceItems> invoiceData = itemsRepository.findById(id);
        if (invoiceData.isPresent()) return invoiceData.get();
        else throw new EntityNotFoundException(InvoiceItems.class, "id", id);
    }

    public InvoiceItems save(InvoiceItems invoice) {
        invoice.setCreatedAt(LocalDateTime.now());
        return itemsRepository.save(invoice);
    }

    public InvoiceItems update(String id, InvoiceItems updatedInvoice) {
        Optional<InvoiceItems> invoiceData = itemsRepository.findById(id);

        if (invoiceData.isPresent()) {
            InvoiceItems dbInvoice = invoiceData.get();
            String ignored[] = {"id", "createdAt"};
            BeanUtils.copyProperties(updatedInvoice, dbInvoice, ignored);
            return itemsRepository.save(dbInvoice);
        } else throw new EntityNotFoundException(InvoiceItems.class, "id", id);
    }

    public void deleteById(String id) {
        InvoiceItems document = findById(id);
        log.info("Deleting invoice with id {}", id);
        itemsRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        itemsRepository.deleteAll();
    }

    public List<InvoiceItems> findByInvoice(Invoice invoice) {
        List<InvoiceItems> invoiceData = itemsRepository.findByInvoiceId(invoice.getId());

        if (Collections.isEmpty(invoiceData))
            throw new EntityNotFoundException(InvoiceItems.class, "invoice id", invoice.getId());
        else return invoiceData;
    }
}
