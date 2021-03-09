package com.jet.peoplemanagement.controller;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api")
@Api(value = "Controle para gerenciamento de clientes")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    @ApiOperation(value = "Obter todos os clientes paginando")
    public ResponseEntity<Page<Client>> getAllClients(@RequestParam Integer pageNumber, @RequestParam(required = true) Integer pageSize) {
        Page<Client> pageable = clientService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/clients/{id}")
    @ApiOperation(value = "Obter o cliente pelo seu id")
    public ResponseEntity<Client> getClientById(@PathVariable("id") String id) {
        Client clientData = clientService.findById(id);
        return new ResponseEntity<>(clientData, OK);
    }

    @GetMapping("/clients/cnpj/{cnpj}")
    @ApiOperation(value = "Obter o cliente pelo seu cnpj")
    public ResponseEntity<Client> getClientByCnpj(@PathVariable("cnpj") String cnpj) {
        Client clientData = clientService.findByCnpj(cnpj);
        return new ResponseEntity<>(clientData, OK);
    }

    @ApiOperation(value = "Criar um novo cliente")
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        Client _client = clientService.save(client);
        return new ResponseEntity<>(_client, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Alterar um cliente passando seu id")
    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") String id, @RequestBody Client updateClient) {
        Client clientData = clientService.update(id, updateClient);
        return new ResponseEntity<>(clientData, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar um cliente pelo seu id")
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") String id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Deletar todos os clientes")
    @DeleteMapping("/clients")
    public ResponseEntity<HttpStatus> deleteAllClients() {
        clientService.deleteAll();
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Ativar cliente")
    @PostMapping("/clients/activate/{id}")
    public ResponseEntity<HttpStatus> activate(@PathVariable("id") String id) {
        clientService.activate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Inativar cliente")
    @PostMapping("/clients/inactivate/{id}")
    public ResponseEntity<HttpStatus> inactivate(@PathVariable("id") String id) {
        clientService.inactivate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

  /*@GetMapping("/clients/published")
  public ResponseEntity<List<Client>> findByPublished() {
    try {
      List<Client> clients = clientRepository.findByPublished(true);

      if (clients.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(clients, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

}
