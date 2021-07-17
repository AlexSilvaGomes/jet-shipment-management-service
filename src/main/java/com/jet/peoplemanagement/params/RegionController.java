package com.jet.peoplemanagement.params;

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

@CrossOrigin
@RestController
@RequestMapping("/api")
@Api(value = "Controle das regioes por cliente e preço")
public class RegionController {

    @Autowired
    RegionService regionService;

    @GetMapping("/regions")
    @ApiOperation(value = "Obter todos")
    public ResponseEntity<Page<Region>> getAllRegions(@RequestParam Integer pageNumber, @RequestParam(required = true) Integer pageSize) {
        Page<Region> pageable = regionService.findAllPageable(pageNumber, pageSize);
        return new ResponseEntity<>(pageable, OK);
    }

    @GetMapping("/regions/{id}")
    @ApiOperation(value = "Obter pelo seu id")
    public ResponseEntity<Region> getById(@PathVariable("id") String id) {
        Region region = regionService.findById(id);
        return new ResponseEntity<>(region, OK);
    }

    @ApiOperation(value = "Criar um novo")
    @PostMapping("/regions")
    public ResponseEntity<Region> create(@Valid @RequestBody Region region) {
        Region createdRegion = regionService.save(region);
        return new ResponseEntity<>(createdRegion, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Alterar pelo id")
    @PutMapping("/regions/{id}")
    public ResponseEntity<Region> update(@PathVariable("id") String id, @RequestBody Region updateRegion) {
        Region regionData = regionService.update(id, updateRegion);
        return new ResponseEntity<>(regionData, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar pelo seu id")
    @DeleteMapping("/regions/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        regionService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Deletar todos")
    @DeleteMapping("/regions")
    public ResponseEntity<HttpStatus> deleteAll() {
        regionService.deleteAll();
        return new ResponseEntity<>(NO_CONTENT);
    }

    @ApiOperation(value = "Adicionar todos")
    @PostMapping("/regions/list")
    public ResponseEntity<HttpStatus> addAll(@Valid @RequestBody List<Region> regions) {
        regionService.addAll(regions);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
