package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.model.Client;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import rx.Single;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void storeOnDisk(MultipartFile file, Path fileLocation);

    Single<String> storeAllFiles(List<MultipartFile> files, Client client);

    Single<String> handleFilesUpload(List<MultipartFile> files, Client client);

    Stream<Path> loadAll();

    Path loadFromDisk(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}