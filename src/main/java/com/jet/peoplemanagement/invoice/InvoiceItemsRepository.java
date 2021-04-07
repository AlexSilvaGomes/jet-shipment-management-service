package com.jet.peoplemanagement.invoice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InvoiceItemsRepository extends MongoRepository<InvoiceItems, String> {
    List<InvoiceItems> findByInvoice(Invoice invoice);
}
