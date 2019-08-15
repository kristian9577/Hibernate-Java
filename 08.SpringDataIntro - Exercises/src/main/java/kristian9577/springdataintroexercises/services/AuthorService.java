package kristian9577.springdataintroexercises.services;

import org.springframework.stereotype.Service;

import java.io.IOException;


public interface AuthorService {
    void seedAuthors() throws IOException;
}
