package com.lab2.project.service.impl;

import com.lab2.project.model.Author;
import com.lab2.project.model.Book;
import com.lab2.project.model.dto.AuthorDto;
import com.lab2.project.model.enumerations.Category;
import com.lab2.project.model.events.AuthorCreatedEvent;
import com.lab2.project.model.events.BookCreatedEvent;
import com.lab2.project.model.exceptions.AuthorNotFoundException;
import com.lab2.project.model.exceptions.CategoryNotFoundException;
import com.lab2.project.repository.AuthorRepository;
import com.lab2.project.repository.BookRepository;
import com.lab2.project.repository.views.BooksPerAuthorViewRepository;
import com.lab2.project.service.AuthorService;
import com.lab2.project.service.BookService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

//    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    //    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AuthorServiceImpl(AuthorRepository authorRepository,ApplicationEventPublisher applicationEventPublisher) {

        this.authorRepository = authorRepository;
        this.applicationEventPublisher=applicationEventPublisher;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

//    @Override
//    @Transactional
//    public Optional<Author> save(String name, String surname, Category category, Long authorId) {
//
//        category = this.bookRepository.findByCategory(category)
//                .orElseThrow(() -> new CategoryNotFoundException());
//        Author author = this.authorRepository.findById(authorId)
//                .orElseThrow(() -> new AuthorNotFoundException(authorId));
//
//        this.bookRepository.deleteByName(name);
//        Book book = new Book(name, category, author, availableCopies);
//        this.bookRepository.save(book);
//        //this.refreshMaterializedView();
//
//        this.applicationEventPublisher.publishEvent(new BookCreatedEvent(book));
//        return Optional.of(book);
//    }

    @Override
    public Optional<Author> save(AuthorDto authorDto) {

//        Author author = this.authorRepository.findById(authorDto.getAuthor())
//                .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthor()));

        this.authorRepository.deleteByName(authorDto.getName());
        Author author = new Author(authorDto.getName(),authorDto.getSurname(),authorDto.getCountry());
        this.authorRepository.save(author);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new AuthorCreatedEvent(author));
        return Optional.of(author);
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }
}
