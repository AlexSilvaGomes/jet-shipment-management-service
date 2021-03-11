package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.Shipment;
import com.jet.peoplemanagement.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import rx.Single;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final ShipmentService shipService;
    private final FileUploadService uploadService;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, ShipmentService shipService, FileUploadService uploadService) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.shipService = shipService;
        this.uploadService = uploadService;
        init();
    }

    @Override
    public void store(MultipartFile file) {

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            //Path filepath = Paths.get(destinationFile.toString(), file.getOriginalFilename());
            file.transferTo(destinationFile);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Single<String> storeAndHandleFiles(List<MultipartFile> files) {

        FileUpload fileUpload = new FileUpload();

        return Single.create(singleSubscriber -> {

            files.stream().forEach(fileItem -> {
                try {
                    store(fileItem);
                    Path loader = load(fileItem.getOriginalFilename());
                    File fileFromDisk = loader.toFile();

                    fileUpload.setClient(new Client());
                    fileUpload.setName(fileFromDisk.getName());
                    uploadService.save(fileUpload);

                    Shipment ship = FileToShipMapper.giveMeShipmentModel(fileFromDisk);
                    shipService.save(ship);

                    fileUpload.setShipmentCode(ship.getShipmentCode());
                    fileUpload.setStatus("OK");

                    if (fileFromDisk.delete()) {
                        log.info("success deleting file {}", fileFromDisk.getName());
                    } else {
                        log.info("error deleting file {}", fileFromDisk.getName());
                    }

                } catch (StorageException e) {
                    log.error("Error saving file. See the message: {} ", e.getMessage());
                    singleSubscriber.onError(e);
                } catch (IOException e) {
                    log.error("Error converting fileUpload to Shipment entry. See the message: {} ", e.getMessage());
                    singleSubscriber.onError(e);
                    fileUpload.setStatus("ERROR");
                    uploadService.update(fileUpload.getId(), fileUpload);
                }

            });
            singleSubscriber.onSuccess("Sucesso");
        });

    }

    private void alternative(MultipartFile file) {

        InputStream inStream = null;
        OutputStream outStream = null;

        try {
            Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            //inStream = file.getResource().getInputStream();
            inStream = new FileInputStream(new File("/home/alex/Desktop/teste.pdf"));

            outStream = new FileOutputStream(destinationFile.toFile());

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
                outStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null || outStream != null) {
                try {
                    inStream.close();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}