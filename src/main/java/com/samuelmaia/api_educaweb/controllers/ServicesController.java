package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.error_response.ErrorResponse;
import com.samuelmaia.api_educaweb.services.external_services.CepService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/services")
public class ServicesController {
    @Autowired
    CepService cepService;

    @GetMapping("/cep/{cep}")
    public ResponseEntity<?> queryCep(@PathVariable("cep") String cep){
        try{
            String data = cepService.queryCep(cep);
            return ResponseEntity.status(HttpStatus.OK).body(data);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "CEP não encontrado"));
        }
    }
}
