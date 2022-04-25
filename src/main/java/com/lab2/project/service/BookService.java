package com.lab2.project.service;

import com.lab2.project.model.Book;
import com.lab2.project.model.dto.BookDto;
import com.lab2.project.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findByName(String name);

    Optional<Category> findCategory(Category category);

    Optional<Book> save(String name,Integer availableCopies,  Category category,Long author);

    Optional<Book> save(BookDto bookDto);

    Optional<Book> edit(Long id, String name,Integer availableCopies, Category category,Long author);

    Optional<Book> edit(Long id, BookDto bookDto);

    void deleteById(Long id);

    void refreshMaterializedView();
}
