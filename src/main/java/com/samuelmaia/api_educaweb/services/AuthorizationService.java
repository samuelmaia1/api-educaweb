package com.samuelmaia.api_educaweb.services;

import com.samuelmaia.api_educaweb.repositories.CompanyRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (studentRepository.findByLogin(username) != null) return studentRepository.findByLogin(username);

        if (instructorRepository.findByLogin(username) != null) return instructorRepository.findByLogin(username);

        if (companyRepository.findByLogin(username) != null) return companyRepository.findByLogin(username);

        return null;
    }
}
