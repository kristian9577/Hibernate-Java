package kristian9577.springdataintroexercises.services.impl;

import kristian9577.springdataintroexercises.entities.*;
import kristian9577.springdataintroexercises.repositories.AuthorRepo;
import kristian9577.springdataintroexercises.repositories.BookRepo;
import kristian9577.springdataintroexercises.repositories.CategoryRepo;
import kristian9577.springdataintroexercises.services.BookService;
import kristian9577.springdataintroexercises.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH =
            "C:\\PROJECTS\\springdataintroexercises\\src\\main\\resources\\files\\books.txt";

    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final CategoryRepo categoryRepo;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, CategoryRepo categoryRepo, FileUtil fileUtil) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.categoryRepo = categoryRepo;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedBooks() throws IOException {

        if (this.bookRepo.count() != 0) {
            return;
        }
        String[] books = this.fileUtil.fileContext(BOOKS_FILE_PATH);
        for (String info : books) {
            String[] params = info.split("\\s+");

            Book book = new Book();

            book.setAuthor(randomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            book.setEditionType(editionType);

            LocalDate localDate = LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setRealeseDate(localDate);

            book.setCopies(Integer.parseInt(params[2]));

            BigDecimal price = new BigDecimal(params[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();

            for (int i = 5; i <= params.length - 1; i++) {
                title.append(params[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            book.setCategories(randomCategories());

            this.bookRepo.saveAndFlush(book);
        }

    }


    @Override
    public List<String> findAllTitles() {
        LocalDate localDate = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepo
                .findAllByRealeseDateAfter(localDate)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());


    }

    @Override
    public List<String> findAllAuthorBefore90() {
        LocalDate localDate = LocalDate.parse("1/1/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));

        return this.bookRepo.findAllByRealeseDateBefore(localDate)
                .stream()
                .map(s -> String.format("%s %s", s.getAuthor().getFirstName(),
                        s.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }


    private Author randomAuthor() {
        Random random = new Random();
        int index = random.nextInt((int) this.authorRepo.count()) + 1;
        return this.authorRepo.getOne(index);
    }

    private Category randomCategory() {
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepo.count()) + 1;
        return this.categoryRepo.getOne(index);
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new LinkedHashSet<>();
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepo.count()) + 1;
        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }
        return categories;
    }
}
