package com.samuelmaia.api_educaweb.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.student.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateStudentToken(Student student){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("api-educaweb").withSubject(student.getLogin()).withExpiresAt(this.generateExpirationDate()).sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException("Erro durante geração do token", e);
        }
    }

    public String generateInstructorToken(Instructor instructor){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("api-educaweb").withSubject(instructor.getLogin()).withExpiresAt(this.generateExpirationDate()).sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException("Erro durante geração do token", e);
        }
    }

    public String validateToken(String token){
        try{
            return JWT.require(Algorithm.HMAC256(secret)).withIssuer("api-educaweb").build().verify(token).getSubject();
        } catch (JWTVerificationException e){
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
    }

}
