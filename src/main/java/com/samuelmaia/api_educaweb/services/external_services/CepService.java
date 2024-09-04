package com.samuelmaia.api_educaweb.services.external_services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    public String queryCep(String cep){
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity("https://viacep.com.br/ws/"+ cep +"/json/", String.class);
        String responseBody = response.getBody();
        if (responseBody.contains("erro")) {
            throw new EntityNotFoundException("Cep não encontrado");
        }
        return responseBody;
    }

}
