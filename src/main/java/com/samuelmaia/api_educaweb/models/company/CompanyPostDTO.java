package com.samuelmaia.api_educaweb.models.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CompanyPostDTO {
    private String name;
    private String email;
    private String password;
    private String state;
    private String city;
    private String address;
}
