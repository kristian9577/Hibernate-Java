package kristian9577.springdataintroexercises.services.impl;

import kristian9577.springdataintroexercises.entities.Author;
import kristian9577.springdataintroexercises.repositories.AuthorRepo;
import kristian9577.springdataintroexercises.services.AuthorService;
import kristian9577.springdataintroexercises.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final static String AUTHOR_FILE_PATH =
            "C:\\PROJECTS\\springdataintroexercises\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepo authorRepo;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepo authorRepo, FileUtil fileUtil) {
        this.authorRepo = authorRepo;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {

        if(this.authorRepo.count() !=0){
            return; //to not insert twice authors
        }

        String[] authors = this.fileUtil.fileContext(AUTHOR_FILE_PATH);

        for (String s : authors) {
            String[] params = s.split("\\s+");
            Author author = new Author();
            author.setFirstName(params[0]);
            author.setLastName(params[1]);

        this.authorRepo.saveAndFlush(author);
        }
    }
}
