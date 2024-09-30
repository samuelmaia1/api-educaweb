package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.components.SecurityFilter;
import com.samuelmaia.api_educaweb.models.company.Company;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.response.AuthorizationResponse;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.response.ValidationResponse;
import com.samuelmaia.api_educaweb.models.student.Student;
import com.samuelmaia.api_educaweb.repositories.CompanyRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.repositories.StudentRepository;
import com.samuelmaia.api_educaweb.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    TokenService tokenService;

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @PostMapping("/validatetoken")
    public ResponseEntity<?> validateToken(HttpServletRequest request){

        String token = securityFilter.recoverToken(request);

        Instructor instructor;
        Student student;
        Company company;

        if (token != null){
            if (tokenService.validateToken(token).isEmpty()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(401, "Login expirado."));
            }

            var subject = this.tokenService.validateToken(token);

            UserDetails user = studentRepository.findByLogin(subject);

            if (user == null){
                user = instructorRepository.findByLogin(subject);
            }

            if (user == null){
                user = instructorRepository.findByLogin(subject);
            }

            if (user != null){
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            return ResponseEntity.ok(new ValidationResponse(token, user.getUsername(), user
                    .getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("")));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Login não efetuado"));
    }
}
