package kristian9577.springdataintroexercises.services.impl;

import kristian9577.springdataintroexercises.entities.Category;
import kristian9577.springdataintroexercises.repositories.CategoryRepo;
import kristian9577.springdataintroexercises.services.CategoryService;
import kristian9577.springdataintroexercises.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static String CATEFORY_FILE_PATH =
            "C:\\PROJECTS\\springdataintroexercises\\src\\main\\resources\\files\\categories.txt";

    private final CategoryRepo categoryRepo;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, FileUtil fileUtil) {
        this.categoryRepo = categoryRepo;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedCategory() throws IOException {
        if (this.categoryRepo.count() != 0) {
            return;
        }

        String[] categories = this.fileUtil.fileContext(CATEFORY_FILE_PATH);

        for (String info : categories) {
            Category category = new Category();
            category.setName(info);
            this.categoryRepo.saveAndFlush(category);
        }
    }
}
