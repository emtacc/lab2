package com.lab2.project.repository;

import com.lab2.project.model.Author;
import com.lab2.project.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findById(Long id);

    void deleteByName(String name);
}
