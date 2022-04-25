package com.lab2.project.model.dto;

import com.lab2.project.model.Country;
import com.lab2.project.model.enumerations.Category;
import lombok.Data;

@Data
public class AuthorDto {

    private String name;

    private String surname;

    private Country country;



    public AuthorDto() {
    }

    public AuthorDto(String name, String surname, Country country) {
        this.name = name;
        this.surname = surname;
        this.country = country;

    }
}