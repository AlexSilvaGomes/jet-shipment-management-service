package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.service.ShipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(value = "Controle para upload de arquivos")
@Slf4j
public class FileUploadController {

    private final StorageService storageService;
    private final ShipmentService shipmentService;

    @Autowired
    public FileUploadController(StorageService storageService, ShipmentService shipmentService) {
        this.storageService = storageService;
        this.shipmentService = shipmentService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
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
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);
        //Path loader = storageService.load(file.getOriginalFilename());

        //File myTestFile =  new File(loader.toFile().getPath());
        //Shipment ship = FileToShipMapper.giveMeShipmentModel(myTestFile);

        //shipmentService.save(ship);

        //redirectAttributes.addFlashAttribute("message","You successfully uploaded " + file.getOriginalFilename() + "!");

        return new ResponseEntity<>("Ok", HttpStatus.OK);

        //return "redirect:/";
    }

    @ApiOperation(value = "Load file")
    @PostMapping("/loadFiles")
    public ResponseEntity<String> handleFilesUpload(@RequestParam("file") List<MultipartFile> files,
                                                   @RequestParam String clientName,
                                                   @RequestParam String clientId) throws IOException {

        //Arrays.asList(files).stream().forEach(storageService::store);

        storageService.storeAndHandleFiles(files);

        /*Path loader = storageService.load(files[0].getOriginalFilename());
        File file =  loader.toFile();
        Shipment ship = FileToShipMapper.giveMeShipmentModel(file);

        shipmentService.save(ship);*/

       /* if (file.delete()) {
            log.info("success deleting file {}", file.getName());
        } else {
            log.info("error deleting file {}", file.getName());
        }*/

        //storageService.store(file);
        //redirectAttributes.addFlashAttribute("message","You successfully uploaded " + file.getOriginalFilename() + "!");

        return new ResponseEntity<>("Ok", HttpStatus.OK);

        //return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}