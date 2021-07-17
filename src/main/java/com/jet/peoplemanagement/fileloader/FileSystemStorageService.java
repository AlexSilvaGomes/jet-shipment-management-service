package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.shipmentStatus.ShipmentStatus;
import com.jet.peoplemanagement.shipmentStatus.ShipmentStatusService;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipment.Shipment;
import com.jet.peoplemanagement.shipment.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    public static final String ERROR = "ERROR";
    public static final String ERROR_DIR = "error";
    public static final String SYSTEM_ADMIN = "Administrador do sistema";
    private final Path rootLocation;
    private final ShipmentService shipService;
    private final ShipmentStatusService deliveryService;
    private final FileUploadService uploadService;
    private Path clientLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, ShipmentService shipService, ShipmentStatusService deliveryService, FileUploadService uploadService) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.shipService = shipService;
        this.deliveryService = deliveryService;
        this.uploadService = uploadService;
        init();
    }

    @Override
    public void storeOnDisk(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void storeOnDisk(MultipartFile file, Client client) {

        Path fileLocation = getClientPath(client.getName());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = fileLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(fileLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }
            //Path filepath = Paths.get(destinationFile.toString(), file.getOriginalFilename());
            //file.transferTo(destinationFile);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Single<String> storeAllFiles(List<MultipartFile> files, Client client) {

        return Single.create(singleSubscriber -> {
            files.stream().forEach(fileItem -> storeOnDisk(fileItem, client));
            singleSubscriber.onSuccess("Sucesso");
        });
    }

    @Override
    public Single<String> handleFilesUpload(List<Path> files, Client client) {

        return Single.create(singleSubscriber -> {
            files.forEach(fileItem -> {
                handleSingleFile(client, fileItem);
            });

            singleSubscriber.onSuccess("Sucesso");
        });

    }

    @Override
    public void handleSingleFile(Client client, Path fileItem) {
        FileUpload fileSaved = new FileUpload();
        File fileFromDisk = null;

        try {
            fileSaved = storeOnDb(client, fileItem.toString());
            fileFromDisk = loadFromDisk(client, fileItem.toString()).toFile();
            List<Shipment> shipList = createShipments(client, fileFromDisk);
            saveFileUploadShipmentCode(fileSaved, shipList);
            deleteFileFromDisk(fileFromDisk);

        } catch (StorageException e) {
            log.error("Error saving file. See the message: {} ", e.getMessage());
            updateFileUploadStatus(fileSaved, ERROR, e.getMessage());

        } catch (IOException e) {
            log.error("Error converting fileUpload to Shipment entry. See the message: {} ", e.getMessage());
            copyFileToErrorDirectory(fileFromDisk, client.getName());
            updateFileUploadStatus(fileSaved, ERROR, e.getMessage());

        } catch (Exception e) {
            log.error("General error importing file. See the message: {} ", e.getMessage());
            copyFileToErrorDirectory(fileFromDisk, client.getName());
            updateFileUploadStatus(fileSaved, ERROR, e.getMessage());
        }
    }

    @Override
    public void handleSingleFileCall(Client client, MultipartFile file) throws IOException {
        FileUpload fileSaved = new FileUpload();
        File fileFromDisk = null;

        try {
            storeOnDisk(file, client);
            fileSaved = storeOnDb(client, file.getOriginalFilename());
            fileFromDisk = loadFromDisk(client, file.getOriginalFilename()).toFile();
            List<Shipment> shipList = createShipments(client, fileFromDisk);
            saveFileUploadShipmentCode(fileSaved, shipList);
            deleteFileFromDisk(fileFromDisk);

        } catch (IllegalArgumentException e) {
            log.error("Formato do arquivo inválido: {} ", e.getMessage());
            updateFileUploadStatus(fileSaved, ERROR, e.getMessage());
            throw e;

        } catch (StorageException e) {
            log.error("Error saving file. See the message: {} ", e.getMessage());
            updateFileUploadStatus(fileSaved, ERROR, e.getMessage());
            throw e;

        } catch (IOException e) {
            log.error("Error converting fileUpload to Shipment entry. See the message: {} ", e.getMessage());
            copyFileToErrorDirectory(fileFromDisk, client.getName());
            updateFileUploadStatus(fileSaved, ERROR, e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("General error importing file. See the message: {} ", e.getMessage());
            copyFileToErrorDirectory(fileFromDisk, client.getName());
            updateFileUploadStatus(fileSaved, ERROR, e instanceof DuplicateKeyException ? "Arquivo com o mesmo código de envio já recebido": e.getMessage());
            throw e;
        }
    }

    private void copyFileToErrorDirectory(File fileFromDisk, String clientName) {
        Path clientPath = getClientPath(clientName);

        Path errorPath = getErrorPath(clientPath.resolve("").toString(), ERROR_DIR);
        Path destinationFile = errorPath.resolve(fileFromDisk.getName()).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(errorPath.toAbsolutePath())) {
            throw new StorageException("Cannot store file outside current directory.");
        }
        try (InputStream inputStream = new FileInputStream(fileFromDisk.getPath())) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        deleteFileFromDisk(fileFromDisk);
    }

    @Transactional
    private List<Shipment> createShipments(Client client, File fileFromDisk) throws IOException {
        List<Shipment> shipList = FileToShipMapper.giveMeShipmentModel(fileFromDisk);

        for (Shipment ship : shipList) {
            ship.setClient(client);

            Shipment shipSaved = shipService.save(ship);
            createShipmentStatus(shipSaved);
        }

        return shipList;
    }

    private void createShipmentStatus(Shipment shipSaved) {
        ShipmentStatus delivery = new ShipmentStatus();
        delivery.setShipmentCode(shipSaved.getShipmentCode());
        delivery.setStatus( DeliveryStatusEnum.POSTADO);
        String name = nonNull(shipSaved.getClient()) ? shipSaved.getClient().getName() : SYSTEM_ADMIN;
        delivery.setStatusResponsibleName(name);
        try {
            deliveryService.justSave(delivery);
        } catch (Exception e) {
            shipService.deleteById(shipSaved.getId());
            throw e;
        }
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

    private void saveFileUploadShipmentCode(FileUpload fileSaved, List<Shipment> shipList) {

        String shipmentCodes =  shipList.stream()
                .map( shipment -> shipment.getShipmentCode() )
                .collect( Collectors.joining( "," ) );

        fileSaved.setShipmentCode(shipmentCodes);
        fileSaved.setAmount(shipList.size());
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
    public List<Path> loadAll(String clientName) {
        try {
            Path location = getClientPath(clientName);
            Path errorPath = getErrorPath(location.resolve("").toString(), ERROR_DIR);
            return Files.walk(location, 1)
                    .filter(path -> !path.equals(location))
                    .filter(path -> !path.equals(errorPath))
                    .map(location::relativize)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path loadFromDisk(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Path loadFromDisk(Client client, String filename) {
        Path clientPath = Paths.get(rootLocation.getFileName().toString() , client.getName());
        return clientPath.resolve(filename);
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

    public Path getClientPath(String name) {
        try {
            return Files.createDirectories( Paths.get(rootLocation.getFileName().toString() , name));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public Path getErrorPath(String parent, String directory) {
        try {
            return Files.createDirectories(Paths.get(parent, directory));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}