package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... strings) throws Exception {
//        this.authorService.seedAuthors();
//        this.categoryService.seedCategories();
//        this.bookService.seedBooks();
//        this.bootTitlesByAgeRestriction();
//        this.printBookByPrice();
//            this.printBookNotRealesedIn();

        this.printBooksByAuthor();
    }

    public void bootTitlesByAgeRestriction() throws IOException {
        List<String> title = this.bookService.getAllBookTitleWithAgeRes();

        title.forEach(System.out::println);
    }

    private void printBookByPrice() {
        System.out.println(this.bookService.getAllBooksPrice());
    }

    private void printBookNotRealesedIn() throws IOException {
        System.out.println(this.bookService.getAllBooksNotRealesedIn());
    }

    private void printBooksByAuthor() throws IOException {
        System.out.println(this.bookService.getAllBooksByAuthor());
    }
}
