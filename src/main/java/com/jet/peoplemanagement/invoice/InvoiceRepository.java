package com.jet.peoplemanagement.invoice;


import com.jet.peoplemanagement.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
    List<Invoice> findByClient(Client client);
    List<Invoice> findByStatus(InvoiceStatusEnum status);
    Optional<Invoice> findTopByOrderByUpdatedAtDesc();
}
