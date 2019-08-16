package kristian9577.jsonexercises.service;

import kristian9577.jsonexercises.domain.dto.CategorySeedDto;

public interface CategoryService {

    void seedCategories(CategorySeedDto[] categorySeedDtos);
}
