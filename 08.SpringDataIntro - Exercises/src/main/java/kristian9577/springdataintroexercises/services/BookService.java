package kristian9577.springdataintroexercises.services;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> findAllTitles();

    List<String>findAllAuthorBefore90();
}
