package com.jet.peoplemanagement.shipment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Api(value = "Controle para gerenciamento de entregas/envios")
public class ShipmentController {

    @Autowired
    ShipmentService shipmentService;

    @GetMapping("/shipments")
    @ApiOperation(value = "Obter todos os envios paginando")
    public ResponseEntity<Page<Shipment>> getAllShipments(@RequestParam Integer pageNumber, @RequestParam(required = true) Integer pageSize) {
        Page<Shipment> pageable = shipmentService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/shipments/{id}")
    @ApiOperation(value = "Obter o envio pelo seu id")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable("id") String id) {
        Shipment shipmentData = shipmentService.findById(id);
        return new ResponseEntity<>(shipmentData, OK);
    }

    @GetMapping("/shipments/shipmentCode/{shipmentCode}")
    @ApiOperation(value = "Obter o envio pelo seu código")
    public ResponseEntity<Shipment> getShipmentByShipmentCode(@PathVariable("shipmentCode") String shipmentCode) {
        Shipment shipmentData = shipmentService.findByShipmentCode(shipmentCode);
        return new ResponseEntity<>(shipmentData, OK);
    }

    @GetMapping("/shipments/shipmentCodeLike/{shipmentCode}")
    @ApiOperation(value = "Obter o envio pelo seu código")
    public ResponseEntity<Page<Shipment>> getShipmentByShipmentCodeLike(@PathVariable("shipmentCode") String shipmentCode) {
        Page<Shipment> shipmentData = shipmentService.findByShipmentCodeLike(shipmentCode);
        return new ResponseEntity<>(shipmentData, OK);
    }

    @ApiOperation(value = "Criar um novo envio")
    @PostMapping("/shipments")
    public ResponseEntity<Shipment> createShipment(@Valid @RequestBody Shipment shipment) {
        Shipment _shipment = shipmentService.save(shipment);
        return new ResponseEntity<>(_shipment, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Alterar um envio passando seu id")
    @PutMapping("/shipments/{id}")
    public ResponseEntity<Shipment> updateShipment(@Valid @PathVariable("id") String id, @RequestBody Shipment updateShipment) {
        Shipment shipmentData = shipmentService.update(id, updateShipment);
        return new ResponseEntity<>(shipmentData, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar um envio pelo seu id")
    @DeleteMapping("/shipments/{id}")
    public ResponseEntity<HttpStatus> deleteShipment(@PathVariable("id") String id) {
        shipmentService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Deletar todos os envios")
    @DeleteMapping("/shipments")
    public ResponseEntity<HttpStatus> deleteAllShipments() {
        shipmentService.deleteAll();
        return new ResponseEntity<>(NO_CONTENT);
    }

   /* @ApiOperation(value = "Ativar envio")
    @PostMapping("/shipments/activate/{id}")
    public ResponseEntity<HttpStatus> activate(@PathVariable("id") String id) {
        shipmentService.activate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Inativar envio")
    @PostMapping("/shipments/inactivate/{id}")
    public ResponseEntity<HttpStatus> inactivate(@PathVariable("id") String id) {
        shipmentService.inactivate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }*/

  /*@GetMapping("/shipments/published")
  public ResponseEntity<List<Shipment>> findByPublished() {
    try {
      List<Shipment> shipments = shipmentRepository.findByPublished(true);

      if (shipments.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(shipments, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

}
