package com.jet.peoplemanagement.controller;

import com.jet.peoplemanagement.model.Provider;
import com.jet.peoplemanagement.service.ProviderService;
import com.jet.peoplemanagement.user.UserType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Api(value = "Controle para gerenciamento de provedores de serviço")
public class ProviderController {

    @Autowired
    ProviderService providerService;

    @GetMapping("/providers")
    @ApiOperation(value = "Obter todos os provedores paginando")
    public ResponseEntity<Page<Provider>> getAllProviders(@RequestParam Integer pageNumber, @RequestParam(required = true) Integer pageSize) {
        Page<Provider> pageable = providerService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/providers/{id}")
    @ApiOperation(value = "Obter o provedor pelo seu id")
    public ResponseEntity<Provider> getProviderById(@PathVariable("id") String id) {
        Provider providerData = providerService.findById(id);
        return new ResponseEntity<>(providerData, OK);
    }

    @ApiOperation(value = "Criar um novo provedor de serviço")
    @PostMapping("/providers")
    public ResponseEntity<Provider> createProvider(@Valid @RequestBody Provider provider) {
        provider.setType(UserType.PROVIDER.getName());
        Provider _provider = providerService.save(provider);
        return new ResponseEntity<>(_provider, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Alterar um provedor de serviço passando seu id")
    @PutMapping("/providers/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable("id") String id, @RequestBody Provider updateProvider) {
        Provider providerData = providerService.update(id, updateProvider);
        return new ResponseEntity<>(providerData, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar um provedor pelo seu id")
    @DeleteMapping("/providers/{id}")
    public ResponseEntity<HttpStatus> deleteProvider(@PathVariable("id") String id) {
        providerService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Deletar todos os provedores")
    @DeleteMapping("/providers")
    public ResponseEntity<HttpStatus> deleteAllProviders() {
        providerService.deleteAll();
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Ativar um provedor")
    @PostMapping("/providers/activate/{id}")
    public ResponseEntity<HttpStatus> activate(@PathVariable("id") String id) {
        providerService.activate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Inativar um provedor")
    @PostMapping("/providers/inactivate/{id}")
    public ResponseEntity<HttpStatus> inactivate(@PathVariable("id") String id) {
        providerService.inactivate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

  /*@GetMapping("/providers/published")
  public ResponseEntity<List<Provider>> findByPublished() {
    try {
      List<Provider> providers = providerRepository.findByPublished(true);

      if (providers.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(providers, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

}
