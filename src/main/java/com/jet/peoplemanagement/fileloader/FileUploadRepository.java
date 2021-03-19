package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileUploadRepository extends MongoRepository<FileUpload, String> {
    Optional<FileUpload> findByShipmentCode(String shipmentCode);

    List<FileUpload> findByClient(Client client, LocalDateTime when);

    List<FileUpload> findByClientAndCreatedAtBetween(Client client, LocalDateTime init, LocalDateTime end);

    Optional<FileUpload> findByName(String name);
}
