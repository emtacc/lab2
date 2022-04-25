package com.lab2.project.model.dto;

import com.lab2.project.model.enumerations.Category;
import lombok.Data;

@Data
public class BookDto {



    private String name;

    private Integer availableCopies;

    private Category category;

    private Long author;

    public BookDto() {
    }

    public BookDto(String name, Integer availableCopies, Category category, Long author) {

        this.name = name;
        this.availableCopies = availableCopies;
        this.category = category;
        this.author = author;
    }
}

