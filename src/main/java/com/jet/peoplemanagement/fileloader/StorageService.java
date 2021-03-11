package com.jet.peoplemanagement.fileloader;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import rx.Single;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    Single<String> storeAndHandleFiles(List<MultipartFile> files);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}