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

    public static final String ERROR = "ERROR";
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
    public void storeOnDisk(MultipartFile file, Path fileLocation) {

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = fileLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(fileLocation.toAbsolutePath())) {
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
    public Single<String> storeAllFiles(List<MultipartFile> files, Client client) {
        Path fileLocation = initClientPath(client.getName());
        return Single.create(singleSubscriber -> {
            files.stream().forEach(fileItem -> storeOnDisk(fileItem, fileLocation));
            singleSubscriber.onSuccess("Sucesso");
        });
    }

    @Override
    public Single<String> handleFilesUpload(List<MultipartFile> files, Client client) {

        return Single.create(singleSubscriber -> {
            files.stream().forEach(fileItem -> {

                FileUpload fileSaved = new FileUpload();
                try {
                    fileSaved = storeOnDb(client, fileItem.getOriginalFilename());
                    storeOnDisk(fileItem);
                    File fileFromDisk = loadFromDisk(fileItem.getOriginalFilename()).toFile();

                    Shipment ship = createShipment(fileFromDisk);
                    saveFileUploadShipmentCode(fileSaved, ship);
                    deleteFileFromDisk(fileFromDisk);

                } catch (StorageException e) {
                    log.error("Error saving file. See the message: {} ", e.getMessage());
                    updateFileUploadStatus(fileSaved, ERROR, e.getMessage());

                } catch (IOException e) {
                    log.error("Error converting fileUpload to Shipment entry. See the message: {} ", e.getMessage());
                    updateFileUploadStatus(fileSaved, ERROR, e.getMessage());

                } catch (Exception e) {
                    log.error("General error importing file. See the message: {} ", e.getMessage());
                    updateFileUploadStatus(fileSaved, ERROR, e.getMessage());
                }
            });

            singleSubscriber.onSuccess("Sucesso");
        });

    }

    private Shipment createShipment(File fileFromDisk) throws IOException {
        Shipment ship = FileToShipMapper.giveMeShipmentModel(fileFromDisk);
        shipService.save(ship);
        return ship;
    }

    private void updateFileUploadStatus(FileUpload fileSaved, String status, String message) {
        fileSaved.setStatus(status);
        fileSaved.setMessage(message);
        uploadService.update(fileSaved.getId(), fileSaved);
    }

    private FileUpload storeOnDb(Client client, String fileName) {
        FileUpload fileUpload = new FileUpload();
        fileUpload.setClient(client);
        fileUpload.setName(fileName);
        FileUpload fileSaved = uploadService.save(fileUpload);
        return fileSaved;
    }

    private void saveFileUploadShipmentCode(FileUpload fileSaved, Shipment ship) {
        fileSaved.setShipmentCode(ship.getShipmentCode());
        updateFileUploadStatus(fileSaved, "OK", "");
    }

    private void deleteFileFromDisk(File fileFromDisk) {
        if (fileFromDisk.delete()) {
            log.info("success deleting file {}", fileFromDisk.getName());
        } else {
            log.info("error deleting file {}", fileFromDisk.getName());
        }
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
    public Path loadFromDisk(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = loadFromDisk(filename);
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

    public Path initClientPath(String name) {
        try {
            return Files.createDirectories( Paths.get(rootLocation.getFileName().toString() , name));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}