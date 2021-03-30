package com.jet.peoplemanagement.delivery;

import com.jet.peoplemanagement.delivery.Delivery;
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
@Api(value = "Controle para gerenciamento de entregas")
public class DeliveryController {

    @Autowired
    DeliveryService shipmentService;

    @GetMapping("/deliveries")
    @ApiOperation(value = "Obter todos os envios paginando")
    public ResponseEntity<Page<Delivery>> getAll(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        Page<Delivery> pageable = shipmentService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/deliveries/{id}")
    @ApiOperation(value = "Obter o envio pelo seu id")
    public ResponseEntity<Delivery> getById(@PathVariable("id") String id) {
        Delivery shipmentData = shipmentService.findById(id);
        return new ResponseEntity<>(shipmentData, OK);
    }

    @GetMapping("/deliveries/shipmentCode/{shipmentCode}")
    @ApiOperation(value = "Obter o envio pelo seu id")
    public ResponseEntity<List<Delivery>> getByShipmentCode(@PathVariable("shipmentCode") String shipmentCode) {
        List<Delivery> data = shipmentService.findByShipmentCode(shipmentCode);
        return new ResponseEntity<>(data, OK);
    }

    @ApiOperation(value = "Criar um novo envio")
    @PostMapping("/deliveries")
    public ResponseEntity<Delivery> create(@Valid @RequestBody Delivery delivery) {
        Delivery _shipment = shipmentService.save(delivery);
        return new ResponseEntity<>(_shipment, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Alterar um envio passando seu id")
    @PutMapping("/deliveries/{id}")
    public ResponseEntity<Delivery> update(@Valid @PathVariable("id") String id, @RequestBody Delivery updateShipment) {
        Delivery shipmentData = shipmentService.update(id, updateShipment);
        return new ResponseEntity<>(shipmentData, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar um envio pelo seu id")
    @DeleteMapping("/deliveries/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        shipmentService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Deletar todos os envios")
    @DeleteMapping("/deliveries")
    public ResponseEntity<HttpStatus> deleteAll() {
        shipmentService.deleteAll();
        return new ResponseEntity<>(NO_CONTENT);
    }

   /* @ApiOperation(value = "Ativar envio")
    @PostMapping("/deliveries/activate/{id}")
    public ResponseEntity<HttpStatus> activate(@PathVariable("id") String id) {
        shipmentService.activate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Inativar envio")
    @PostMapping("/deliveries/inactivate/{id}")
    public ResponseEntity<HttpStatus> inactivate(@PathVariable("id") String id) {
        shipmentService.inactivate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }*/

  /*@GetMapping("/deliveries/published")
  public ResponseEntity<List<Delivery>> findByPublished() {
    try {
      List<Delivery> deliveries = shipmentRepository.findByPublished(true);

      if (deliveries.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(deliveries, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

}
