package kristian9577.jsonexercises.service.impl;

import kristian9577.jsonexercises.domain.dto.CategorySeedDto;
import kristian9577.jsonexercises.domain.entity.Category;
import kristian9577.jsonexercises.repository.CategoryRepo;
import kristian9577.jsonexercises.service.CategoryService;
import kristian9577.jsonexercises.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {


    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, CategoryRepo categoryRepo) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if (!validatorUtil.isValid(categorySeedDto)) {
                validatorUtil.violations(categorySeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            Category category = this.modelMapper.map(categorySeedDto, Category.class);
            this.categoryRepo.saveAndFlush(category);
        }
    }
}
