package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
    public ResponseEntity<Page<Shipment>> getAllShipments(@RequestParam(required = false) String clientId, @RequestParam Integer pageNumber, @RequestParam(required = true) Integer pageSize) {

        Page<Shipment> pageable = StringUtils.isBlank(clientId) ?
                shipmentService.findAll(pageNumber, pageSize) :
                shipmentService.findAllByClient(new Client(clientId), pageNumber, pageSize);

        return new ResponseEntity<>(pageable, OK);
    }

/*    @GetMapping("/shipments/client")
    @ApiOperation(value = "Obter todos os envios paginando por cliente")
    public ResponseEntity<Page<Shipment>> getAllShipmentsByClient(@RequestBody Client client, @RequestParam Integer pageNumber, @RequestParam(required = true) Integer pageSize) {
        Page<Shipment> pageable = shipmentService.findAllByClient(client, pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }*/

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

    @GetMapping("/shipments/filterByProvider")
    @ApiOperation(value = "Obter o envio pelo seu código")
    public ResponseEntity<List<Shipment>> getShipmentByProvider(@RequestParam("providerId") String providerId, @RequestParam("status") DeliveryStatusEnum status) {
        List<Shipment> shipmentData = shipmentService.findByProviderAndStatus(providerId, status);
        return new ResponseEntity<>(shipmentData, OK);
    }

//    @GetMapping("/shipments/filter")
//    @ApiOperation(value = "Obter o envio pelo seu código")
//    public ResponseEntity<Shipment> filterShipmentByShipmentCode(@RequestParam("shipCode") String shipmentCode) {
//        Shipment shipmentData = shipmentService.findByShipmentCode(shipmentCode);
//        return new ResponseEntity<>(shipmentData, OK);
//    }

    @GetMapping("/shipments/shipmentCodeLike/{shipmentCode}")
    @ApiOperation(value = "Obter os envios pelo seu código operador like  e clientId opcional")
    public ResponseEntity<Page<Shipment>> getShipmentByShipmentCodeLike(@RequestParam(required = false) String clientId, @PathVariable("shipmentCode") String shipmentCode) {

        Page<Shipment> shipmentData = StringUtils.isEmpty(clientId) ? shipmentService.findByShipmentCodeLike(shipmentCode) :
                shipmentService.findByShipmentCodeLikeAndClient(shipmentCode, new Client(clientId));

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
