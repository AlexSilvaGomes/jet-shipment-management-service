package com.jet.peoplemanagement.invoice;

import com.jet.peoplemanagement.exception.BusinessException;
import com.jet.peoplemanagement.exception.EntityNotFoundException;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.client.ClientService;
import com.jet.peoplemanagement.shipment.Shipment;
import com.jet.peoplemanagement.shipment.ShipmentService;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jet.peoplemanagement.invoice.InvoiceStatusEnum.GERADO;
import static com.jet.peoplemanagement.invoice.InvoiceStatusEnum.VISUALIZACAO;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsService itemsService;

    @Autowired
    ShipmentService shipService;

    @Autowired
    ClientService clientService;

    public Page<Invoice> findAll(Integer pageNumber, Integer pageSize) {
        Page<Invoice> pageable = invoiceRepository.findAll(
                PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize,
                        Sort.Direction.DESC, "updatedAt"));

        if (!pageable.hasContent()) throw new EntityNotFoundException(Invoice.class);

        return pageable;
    }

    public Invoice findById(String id) {
        Optional<Invoice> invoiceData = invoiceRepository.findById(id);

        if (invoiceData.isPresent()) {
            Invoice invoiceResult = invoiceData.get();
            return invoiceResult;
        } else throw new EntityNotFoundException(Invoice.class, "id", id);
    }

    public Invoice findByIdItemsIncluded(String id) {
        Optional<Invoice> invoiceData = invoiceRepository.findById(id);

        if (invoiceData.isPresent()) {
            Invoice invoiceResult = invoiceData.get();
            invoiceResult.setItems(itemsService.findByInvoice(invoiceResult));
            return invoiceResult;
        } else throw new EntityNotFoundException(Invoice.class, "id", id);
    }

    public Invoice save(Invoice invoice) {
        invoice.setCreatedAt(LocalDateTime.now());
        return invoiceRepository.save(invoice);
    }

    public Invoice update(String id, Invoice updatedInvoice) {
        Optional<Invoice> invoiceData = invoiceRepository.findById(id);

        if (invoiceData.isPresent()) {
            Invoice dbInvoice = invoiceData.get();
            String ignored[] = {"id", "createdAt"};
            BeanUtils.copyProperties(updatedInvoice, dbInvoice, ignored);
            return invoiceRepository.save(dbInvoice);
        } else throw new EntityNotFoundException(Invoice.class, "id", id);
    }

    public void deleteById(String id) {
        Invoice document = findById(id);
        log.info("Deleting invoice with id {}", id);
        invoiceRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        invoiceRepository.deleteAll();
    }

    public List<Invoice> findByClient(Client client) {
        List<Invoice> invoiceData = invoiceRepository.findByClient(client);

        if (!Collections.isEmpty(invoiceData)) {
            return invoiceData;
        } else throw new EntityNotFoundException(Invoice.class, "client id", client.getId());
    }

    public Page findByCnpj(String cnpj) {
        Client client = clientService.findByCnpj(cnpj);
        List<Invoice> invoiceData = invoiceRepository.findByClient(client);

        if (!Collections.isEmpty(invoiceData)) {
            Page page = new PageImpl(invoiceData, PageRequest.of(0, invoiceData.size()), invoiceData.size());
            return page;
        } else throw new EntityNotFoundException(Invoice.class, "client cnpj", cnpj);
    }

    public Page findByCompanyName(String companyName) {
        Client client = clientService.findByCompanyName(companyName);
        List<Invoice> invoiceData = invoiceRepository.findByClient(client);

        if (!Collections.isEmpty(invoiceData)) {
            Page page = new PageImpl(invoiceData, PageRequest.of(0, invoiceData.size()), invoiceData.size());
            return page;
        } else throw new EntityNotFoundException(Invoice.class, "client companyName", companyName);
    }

    public void updateStatus(String id, InvoiceStatusEnum status) {
        Invoice currentInvoice = findById(id);
        currentInvoice.setStatus(status);
        invoiceRepository.save(currentInvoice);
    }

    public Invoice viewByClient(Client client) {

        Optional<Invoice> lastInvoice = invoiceRepository.findTop1ByClient(client, Sort.by(Sort.Direction.DESC, "updatedAt"));
        List<Shipment> shipments = shipService.findByClientAndOptionalLastInvoice(client, lastInvoice);
        Invoice invoice = buildInvoice(client, lastInvoice, shipments, VISUALIZACAO);

       /* if(!lastInvoice.isPresent()){
            save(invoice);
        }*/

        return invoice;
    }

    public Invoice generateByClient(Client client) {
        Optional<Invoice> lastInvoice = invoiceRepository.findTop1ByClient(client,
                Sort.by(Sort.Direction.DESC, "updatedAt"));

        if(lastInvoice.isPresent() && lastInvoice.get().getPeriodEnd().equals(LocalDate.now())){
            throw new BusinessException("Fatura já gerada para o período!");
        }

        List<Shipment> shipments = shipService.findByClientAndOptionalLastInvoice(client, lastInvoice);
        Invoice invoice = buildInvoice(client, lastInvoice, shipments, GERADO);
        save(invoice);

        return invoice;
    }

    private Invoice buildInvoice(Client client, Optional<Invoice> lastInvoice, List<Shipment> shipments, InvoiceStatusEnum invoiceStatus) {
        Invoice invoice = new Invoice();
        invoice.setStatus(invoiceStatus);

        List<InvoiceItems> items = shipments.stream()
                .filter(shipment -> shipment.getStatus().equals(DeliveryStatusEnum.ENTREGUE))
                .map(ship ->  {
                    return new InvoiceItems(invoice.getId(), ship.getShipmentCode(), ship.getSku(), ship.getPrice());
                }).collect(Collectors.toList());

        if(lastInvoice.isPresent()) {
            invoice.setPeriodInit(lastInvoice.get().getPeriodEnd());
            invoice.setPeriodEnd(LocalDate.now());
        } else{
            if(!shipments.isEmpty()){
                invoice.setPeriodInit(shipments.stream().min(Comparator.comparing(Shipment::getUpdatedAt)).get().getUpdatedAt().toLocalDate());
                //invoice.setPeriodEnd(shipments.stream().max(Comparator.comparing(Shipment::getUpdatedAt)).get().getUpdatedAt().toLocalDate());
                invoice.setPeriodEnd(LocalDate.now());
            }
        }

        invoice.setTotalItems(items.size());
        Double amount = items.stream().filter(item -> item.getPrice() != null).mapToDouble(InvoiceItems::getPrice).sum();
        invoice.setAmount(BigDecimal.valueOf(amount));
        invoice.setItems(items);
        invoice.setClient(clientService.findById(client.getId()));

        return invoice;
    }


}
