package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.service.ShipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import rx.Single;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value = "Controle para upload de arquivos")
@Slf4j
public class FileUploadController {

    private final StorageService storageService;
    private final ShipmentService shipmentService;
    private final FileUploadService uploadService;

    @Autowired
    public FileUploadController(StorageService storageService, ShipmentService shipmentService, FileUploadService uploadService) {
        this.storageService = storageService;
        this.shipmentService = shipmentService;
        this.uploadService = uploadService;
    }

    @GetMapping("/loadedFiles")
    @ApiOperation(value = "Obter todos os arquivos carregados no dia")
    public ResponseEntity<List<FileUpload>> listUploadedFiles(@RequestParam String clientId,
                                                              @RequestParam String clientName) throws IOException {

        Client client = new Client(clientId);
        client.setName(clientName);
        List<FileUpload> items=uploadService.findByClientAndCreatedAt(client, LocalDateTime.now());

        return new ResponseEntity<List<FileUpload>>(items, HttpStatus.OK);
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll("clientNamePassar").stream().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ApiOperation(value = "Load file")
    @PostMapping("/loadFile")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam String clientId,
                                                   @RequestParam String clientName) throws IOException {

        Instant init = Instant.now();

        //Client client = new Client(clientId);
        Client client = new Client(String.valueOf(RandomUtils.nextInt()));
        client.setName(clientName);

        storageService.handleSingleFileCall(client, file);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Load file")
    @PostMapping("/loadFiles")
    public Single<ResponseEntity<String>> handleFilesUpload(@RequestParam("files") List<MultipartFile> files,
                                                            @RequestParam String clientId,
                                                            @RequestParam String clientName) throws IOException {

        Instant init = Instant.now();

        //Client client = new Client(clientId);
        Client client = new Client(String.valueOf(RandomUtils.nextInt()));
        client.setName(clientName);

        return storageService.storeAllFiles(files, client)
                .subscribeOn(Schedulers.io())
                .map(s -> {
                    startFileLoader(files, init, client);
                    return new ResponseEntity<>(HttpStatus.OK);
                });
    }

    private void startFileLoader(List<MultipartFile> files, Instant init, Client client) {
        List<Path> paths = storageService.loadAll(client.getName());
        storageService.handleFilesUpload(paths, client)
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    Instant end = Instant.now();
                    log.info("Total duration {} | for {} files ", Duration.between(init, end), files.size());
                }, throwable -> {
                    log.info("Error importing files");
                });
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}