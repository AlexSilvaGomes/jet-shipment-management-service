package com.jet.peoplemanagement.shipmentStatus;

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
@Api(value = "Controle para gerenciamento de status de entrega")
public class ShipmentStatusController {

    @Autowired
    ShipmentStatusService shipmentStatusService;

    @GetMapping("/shipmentsStatus")
    @ApiOperation(value = "Obter todos os envios paginando")
    public ResponseEntity<Page<ShipmentStatus>> getAll(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        Page<ShipmentStatus> pageable = shipmentStatusService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/shipmentsStatus/{id}")
    @ApiOperation(value = "Obter o envio pelo seu id")
    public ResponseEntity<ShipmentStatus> getById(@PathVariable("id") String id) {
        ShipmentStatus shipmentData = shipmentStatusService.findById(id);
        return new ResponseEntity<>(shipmentData, OK);
    }

    @GetMapping("/shipmentsStatus/shipmentCode/{shipmentCode}")
    @ApiOperation(value = "Obter o envio pelo seu id")
    public ResponseEntity<List<ShipmentStatus>> getByShipmentCode(@PathVariable("shipmentCode") String shipmentCode) {
        List<ShipmentStatus> data = shipmentStatusService.findByShipmentCode(shipmentCode);
        return new ResponseEntity<>(data, OK);
    }

    @ApiOperation(value = "Criar um novo envio")
    @PostMapping("/shipmentsStatus")
    public ResponseEntity<ShipmentStatus> create(@Valid @RequestBody ShipmentStatus delivery) {
        ShipmentStatus _shipment = shipmentStatusService.save(delivery);
        return new ResponseEntity<>(_shipment, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Alterar um envio passando seu id")
    @PutMapping("/shipmentsStatus/{id}")
    public ResponseEntity<ShipmentStatus> update(@Valid @PathVariable("id") String id, @RequestBody ShipmentStatus updateShipment) {
        ShipmentStatus shipmentData = shipmentStatusService.update(id, updateShipment);
        return new ResponseEntity<>(shipmentData, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar um envio pelo seu id")
    @DeleteMapping("/shipmentsStatus/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        shipmentStatusService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Deletar todos os envios")
    @DeleteMapping("/shipmentsStatus")
    public ResponseEntity<HttpStatus> deleteAll() {
        shipmentStatusService.deleteAll();
        return new ResponseEntity<>(NO_CONTENT);
    }

   /* @ApiOperation(value = "Ativar envio")
    @PostMapping("/shipmentsStatus/activate/{id}")
    public ResponseEntity<HttpStatus> activate(@PathVariable("id") String id) {
        shipmentService.activate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Inativar envio")
    @PostMapping("/shipmentsStatus/inactivate/{id}")
    public ResponseEntity<HttpStatus> inactivate(@PathVariable("id") String id) {
        shipmentService.inactivate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }*/

  /*@GetMapping("/shipmentsStatus/published")
  public ResponseEntity<List<ShipmentStatus>> findByPublished() {
    try {
      List<ShipmentStatus> shipmentsStatus = shipmentRepository.findByPublished(true);

      if (shipmentsStatus.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(shipmentsStatus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

}
