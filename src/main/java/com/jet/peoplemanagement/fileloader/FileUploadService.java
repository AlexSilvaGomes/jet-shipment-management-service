package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.exception.EntityNotFoundException;
import com.jet.peoplemanagement.model.Client;
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
public class FileUploadService {

    @Autowired
    FileUploadRepository fileRepository;

    public Page<FileUpload> findAll(Integer pageNumber, Integer pageSize) {
        Page<FileUpload> pageable = fileRepository.findAll(PageRequest.of(isNull(pageNumber) ? 0 : pageNumber, isNull(pageSize) ? 10 : pageSize));

        if (!pageable.hasContent()) throw new EntityNotFoundException(FileUpload.class);

        return pageable;
    }

    public FileUpload findById(String id) {
        Optional<FileUpload> fileData = fileRepository.findById(id);

        if (fileData.isPresent()) return fileData.get();

        else throw new EntityNotFoundException(FileUpload.class, "id", id);
    }

    public FileUpload findByShipmentCode(String shipmentCode) {
        Optional<FileUpload> fileData = fileRepository.findByShipmentCode(shipmentCode);

        if (fileData.isPresent()) return fileData.get();
        else throw new EntityNotFoundException(FileUpload.class, "shipmentCode", shipmentCode);
    }

    public List<FileUpload> findByClientAndCreatedAt(Client client, LocalDateTime when){
        List<FileUpload> items = fileRepository.findByClient(client, when);

        if (!items.isEmpty()) return items;
        else throw new EntityNotFoundException(FileUpload.class, "Client name", client.getName());
    }

    public FileUpload findByName(String name) {
        Optional<FileUpload> fileData = fileRepository.findByName(name);

        if (fileData.isPresent()) return fileData.get();
        else throw new EntityNotFoundException(FileUpload.class, "name", name);
    }

    public FileUpload save(FileUpload file) {
        file.setCreatedAt(LocalDateTime.now());
        return fileRepository.save(file);
    }

    public FileUpload update(String id, FileUpload updatedFileUploadDoc) {
        Optional<FileUpload> fileData = fileRepository.findById(id);

        if (fileData.isPresent()) {
            FileUpload dbFileUploadDoc = fileData.get();
            String ignored [] = {"id", "createdAt"};
            BeanUtils.copyProperties(updatedFileUploadDoc, dbFileUploadDoc, ignored);
            return fileRepository.save(dbFileUploadDoc);
        } else throw new EntityNotFoundException(FileUpload.class, "id", id);
    }

    public void deleteById(String id) {
        FileUpload document = findById(id);
        log.info("Deleting file with id {}", id);
        fileRepository.deleteById(document.getId());
    }

    public void deleteAll() {
        fileRepository.deleteAll();
    }

}
