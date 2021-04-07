package com.jet.peoplemanagement.invoice;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipment.Shipment;
import com.jet.peoplemanagement.shipment.ShipmentService;
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
@Api(value = "Controle para gerenciamento de faturas")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ShipmentService shipService;

    @GetMapping("/invoices/view")
    @ApiOperation(value = "Obter todas as faturasa paginando")
    public ResponseEntity<List<Invoice>> viewByClient(Client client) {
        List<Shipment> invoices = invoiceService.viewByClient(client);



        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/invoices")
    @ApiOperation(value = "Obter todas as faturas paginando")
    public ResponseEntity<Page<Invoice>> getAllInvoices(@RequestParam Integer pageNumber, @RequestParam(required = true) Integer pageSize) {
        Page<Invoice> pageable = invoiceService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/invoices/{id}")
    @ApiOperation(value = "Obter fatura pelo seu id")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") String id) {
        Invoice invoiceData = invoiceService.findById(id);
        return new ResponseEntity<>(invoiceData, OK);
    }

    @GetMapping("/invoices/invoiceCode/{invoiceCode}")
    @ApiOperation(value = "Obter fatura pelo seu cliente")
    public ResponseEntity<List<Invoice>> getInvoiceByClient(@PathVariable("client") Client client) {
        List<Invoice> invoiceData = invoiceService.findByClient(client);
        return new ResponseEntity<>(invoiceData, OK);
    }

  /*  @GetMapping("/invoices/invoiceCodeLike/{invoiceCode}")
    @ApiOperation(value = "Obter fatura pelo seu código")
    public ResponseEntity<Page<Invoice>> getInvoiceByInvoiceCodeLike(@PathVariable("invoiceCode") String invoiceCode) {
        Page<Invoice> invoiceData = invoiceService.findByInvoiceCodeLike(invoiceCode);
        return new ResponseEntity<>(invoiceData, OK);
    }*/

    @ApiOperation(value = "Criar uma nova fatura")
    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice) {
        Invoice _invoice = invoiceService.save(invoice);
        return new ResponseEntity<>(_invoice, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Alterar fatura passando seu id")
    @PutMapping("/invoices/{id}")
    public ResponseEntity<Invoice> updateInvoice(@Valid @PathVariable("id") String id, @RequestBody Invoice updateInvoice) {
        Invoice invoiceData = invoiceService.update(id, updateInvoice);
        return new ResponseEntity<>(invoiceData, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar fatura pelo seu id")
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<HttpStatus> deleteInvoice(@PathVariable("id") String id) {
        invoiceService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Deletar faturas")
    @DeleteMapping("/invoices")
    public ResponseEntity<HttpStatus> deleteAllInvoices() {
        invoiceService.deleteAll();
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Inativar fatura")
    @PostMapping("/invoices/inactivate/{id}")
    public ResponseEntity<HttpStatus> pay(@PathVariable("id") String id) {
        invoiceService.updateStatus(id, InvoiceStatusEnum.PAGO);
        return new ResponseEntity<>(NO_CONTENT);
    }

  /*@GetMapping("/invoices/published")
  public ResponseEntity<List<Invoice>> findByPublished() {
    try {
      List<Invoice> invoices = invoiceRepository.findByPublished(true);

      if (invoices.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(invoices, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

}