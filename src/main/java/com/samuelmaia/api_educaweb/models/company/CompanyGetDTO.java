package com.samuelmaia.api_educaweb.models.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CompanyGetDTO {
    private String name;
    private String email;
    private String country;
    private String state;
    private String city;
    private String address;
}
