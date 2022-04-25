package com.lab2.project.web.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
import com.lab2.project.model.Author;
import com.lab2.project.model.Book;
import com.lab2.project.model.enumerations.Category;
import com.lab2.project.service.AuthorService;
import com.lab2.project.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = {"/","/books"})
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;



    public BookController(BookService bookService,AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;

    }

    @GetMapping
    public String getBookPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bodyContent", "books");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id).isPresent()) {
            Book book = this.bookService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            List<Category> categories=new ArrayList<Category>(Arrays.asList(Category.values()));

//            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "add-book");
            return "master-template";
        }
        return "redirect:/books?error=BookNotFound";
    }

    @GetMapping("/add-form")
    public String addBookPage(Model model) {
        List<Author> authors = this.authorService.findAll();
        List<Category> categories=new ArrayList<Category>(Arrays.asList(Category.values()));
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @GetMapping("/categories")
    public String categoriesPage(Model model) {
        List<Category> categories=new ArrayList<Category>(Arrays.asList(Category.values()));
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "categories");
        return "categories";
    }

    @PostMapping("/add")
    public String saveBook(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Integer availableCopies,
            @RequestParam Category category,
            @RequestParam Long author) {
        if (id != null) {
            this.bookService.edit(id, name, availableCopies, category, author);
        } else {
            this.bookService.save(name, availableCopies, category, author);
        }
        return "redirect:/books";
    }
}
