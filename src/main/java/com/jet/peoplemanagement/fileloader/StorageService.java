package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.model.Client;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import rx.Single;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void init();

    void storeOnDisk(MultipartFile file);

    void storeOnDisk(MultipartFile file, Client fileLocation);

    Single<String> storeAllFiles(List<MultipartFile> files, Client client);

    Single<String> handleFilesUpload(List<Path> files, Client client);

    void handleSingleFile(Client client, Path fileItem);

    void handleSingleFileCall(Client client, MultipartFile file) throws IOException;

    List<Path> loadAll(String clientName);

    Path loadFromDisk(String filename);

    Path loadFromDisk(Client client, String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}