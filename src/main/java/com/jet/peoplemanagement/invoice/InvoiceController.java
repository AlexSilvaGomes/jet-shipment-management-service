package com.jet.peoplemanagement.invoice;

import com.jet.peoplemanagement.model.Client;
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

    @GetMapping("/invoices/view/clientId/{clientId}")
    @ApiOperation(value = "Visualizar a fatura por cliente")
    public ResponseEntity<Invoice> viewLastByClient(@PathVariable("clientId") String clientId) {
        Client client = new Client(clientId);
        Invoice invoice = invoiceService.viewByClient(client);
        return new ResponseEntity<>(invoice, OK);
    }

    @GetMapping("/invoices/generate/clientId/{clientId}")
    @ApiOperation(value = "Emitir fatura por cliente")
    public ResponseEntity<Invoice> generateInvoiceByClient(@PathVariable("clientId") String clientId) {
        Client client = new Client(clientId);
        Invoice invoice = invoiceService.generateByClient(client);
        return new ResponseEntity<>(invoice, OK);
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

    @GetMapping("/invoices/clientId/{clientId}")
    @ApiOperation(value = "Obter fatura pelo seu cliente")
    public ResponseEntity<List<Invoice>> getInvoiceByClient(@PathVariable("client") String clientId) {
        Client client = new Client(clientId);
        List<Invoice> invoiceData = invoiceService.findByClient(client);
        return new ResponseEntity<>(invoiceData, OK);
    }

    @GetMapping("/invoices/filter")
    @ApiOperation(value = "Obter fatura pelo seu cliente")
    public ResponseEntity<Page<Invoice>> getInvoiceByCnpj(@RequestParam String cnpj) {
        Page invoiceData = invoiceService.findByCnpj(cnpj);
        return new ResponseEntity<>(invoiceData, OK);
    }

    @GetMapping("/invoices/filterCompanyName")
    @ApiOperation(value = "Obter fatura pelo nome da empresa")
    public ResponseEntity<Page<Invoice>> getInvoiceByCompanyName(@RequestParam String companyName) {
        Page invoiceData = invoiceService.findByCompanyName(companyName);
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

    @ApiOperation(value = "Pagar fatura")
    @GetMapping("/invoices/pay/{id}")
    public ResponseEntity<HttpStatus> pay(@PathVariable("id") String id) {
        invoiceService.updateStatus(id, InvoiceStatusEnum.PAGO);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Desfazer pagamento fatura")
    @GetMapping("/invoices/unpay/{id}")
    public ResponseEntity<HttpStatus> unpay(@PathVariable("id") String id) {
        invoiceService.updateStatus(id, InvoiceStatusEnum.GERADO);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Pagar todas faturas")
    @PostMapping("/invoices/payAll")
    public ResponseEntity<HttpStatus> payAll(@RequestBody() List<String> listOfId) {
        listOfId.stream().forEach(id -> invoiceService.updateStatus(id, InvoiceStatusEnum.PAGO) );
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Não pagar fatura")
    @GetMapping("/invoices/cancel/{id}")
    public ResponseEntity<HttpStatus> cancel(@PathVariable("id") String id) {
        invoiceService.updateStatus(id, InvoiceStatusEnum.CANCELADO);
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
