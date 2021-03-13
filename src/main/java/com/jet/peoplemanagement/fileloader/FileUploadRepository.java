package com.jet.peoplemanagement.fileloader;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileUploadRepository extends MongoRepository<FileUpload, String> {

    Optional<FileUpload> findByShipmentCode(String shipmentCode);

    Optional<FileUpload> findByName(String name);
}
