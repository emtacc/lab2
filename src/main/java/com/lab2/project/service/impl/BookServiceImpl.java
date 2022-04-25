package com.lab2.project.service.impl;

import com.lab2.project.model.Author;
import com.lab2.project.model.Book;
import com.lab2.project.model.dto.BookDto;
import com.lab2.project.model.enumerations.Category;
import com.lab2.project.model.events.BookCreatedEvent;
import com.lab2.project.model.exceptions.AuthorNotFoundException;
import com.lab2.project.model.exceptions.BookNotFoundException;
import com.lab2.project.model.exceptions.CategoryNotFoundException;
import com.lab2.project.repository.AuthorRepository;
import com.lab2.project.repository.BookRepository;
import com.lab2.project.service.BookService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BookServiceImpl(BookRepository bookRepository,
                              AuthorRepository authorRepository,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.bookRepository.findByName(name);
    }

    @Override
    public Optional<Category> findCategory(Category category) {
        return this.bookRepository.findCategory(category);
    }

    @Override
    @Transactional
    public Optional<Book> save(String name, Integer availableCopies, Category category, Long authorId) {


//        List<Category> categories=new ArrayList<Category>(Arrays.asList(Category.values()));

        Author author = this.authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        this.bookRepository.deleteByName(name);

            Book book = new Book(name, category, author, availableCopies);
            this.bookRepository.save(book);
            this.applicationEventPublisher.publishEvent(new BookCreatedEvent(book));
            return Optional.of(book);

    }

    @Override
    public Optional<Book> save(BookDto bookDto) {

        Author author = this.authorRepository.findById(bookDto.getAuthor())
                .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthor()));

        this.bookRepository.deleteByName(bookDto.getName());
        Book book = new Book(bookDto.getName(), bookDto.getCategory(), author, bookDto.getAvailableCopies());
        this.bookRepository.save(book);


        this.applicationEventPublisher.publishEvent(new BookCreatedEvent(book));
        return Optional.of(book);
    }

    @Override
    @Transactional
    public Optional<Book> edit(Long id,String name, Integer availableCopies, Category category, Long authorId) {

        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setName(name);

        book.setAvailableCopies(availableCopies);

//        category = this.bookRepository.findCategory(category)
//                .orElseThrow(() -> new CategoryNotFoundException());
        book.setCategory(category);

        Author author = this.authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));
        book.setAuthor(author);

        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setName(bookDto.getName());
        book.setAvailableCopies(bookDto.getAvailableCopies());

        Category category = this.bookRepository.findCategory(bookDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException());
        book.setCategory(category);

        Author author = this.authorRepository.findById(bookDto.getAuthor())
                .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthor()));
        book.setAuthor(author);

        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public void refreshMaterializedView() {
//        this.productsPerManufacturerViewRepository.refreshMaterializedView();
    }
}
