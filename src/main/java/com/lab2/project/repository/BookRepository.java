package com.lab2.project.repository;

import com.lab2.project.model.Book;
import com.lab2.project.model.enumerations.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByName(String name);

    Optional<Book> findById(Long id);

    @Query(value="select b.category from Book b WHERE b.category = ?1")
    Optional<Category> findCategory(Category category);

    void deleteByName(String name);
}