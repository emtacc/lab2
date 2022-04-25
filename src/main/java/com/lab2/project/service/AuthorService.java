package com.lab2.project.service;

import com.lab2.project.model.Author;
import com.lab2.project.model.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findById(Long id);

    List<Author> findAll();

    Optional<Author> save(AuthorDto authorDto);

    void deleteById(Long id);
}