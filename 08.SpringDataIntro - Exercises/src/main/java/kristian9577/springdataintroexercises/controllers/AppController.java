package kristian9577.springdataintroexercises.controllers;

import kristian9577.springdataintroexercises.services.AuthorService;
import kristian9577.springdataintroexercises.services.BookService;
import kristian9577.springdataintroexercises.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {


    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public AppController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();

//        getAllBooksAfter2000();

        getAllAuthorsBefore90();
    }

    private void getAllBooksAfter2000() {
        List<String> titles = this.bookService.findAllTitles();
        titles.forEach(System.out::println);
    }

    private void getAllAuthorsBefore90() {
        List<String> authors = this.bookService.findAllAuthorBefore90();
        authors.forEach(System.out::println);
    }
}
