package kristian9577.jsonexercises.service.impl;

import kristian9577.jsonexercises.domain.dto.ProductInRangeDto;
import kristian9577.jsonexercises.domain.dto.ProductSeedDto;
import kristian9577.jsonexercises.domain.entity.Category;
import kristian9577.jsonexercises.domain.entity.Product;
import kristian9577.jsonexercises.domain.entity.User;
import kristian9577.jsonexercises.repository.CategoryRepo;
import kristian9577.jsonexercises.repository.ProductRepo;
import kristian9577.jsonexercises.repository.UserRepo;
import kristian9577.jsonexercises.service.ProductService;
import kristian9577.jsonexercises.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public ProductServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, ProductRepo productRepo, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }


    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {

            productSeedDto.setSeller(this.getRandomSeller());
            productSeedDto.setBuyer(this.getRandomBuyer());
            productSeedDto.setCategories(this.getRandomCategories());

            if (!validatorUtil.isValid(productSeedDto)) {
                validatorUtil.violations(productSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            Product product = this.modelMapper.map(productSeedDto, Product.class);

            this.productRepo.saveAndFlush(product);
        }
    }

    private User getRandomSeller() {
        Random random = new Random();

        int id = random.nextInt((int) (this.userRepo.count() - 1)) + 1;
        return this.userRepo.getOne(id);
    }

    private User getRandomBuyer() {
        Random random = new Random();

        int id = random.nextInt((int) (this.userRepo.count() - 1)) + 1;

        if (id % 4 == 0) {
            return null;
        }

        return this.userRepo.getOne(id);
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int id = random.nextInt((int) (this.categoryRepo.count() - 1)) + 1;
        return this.categoryRepo.findById(id).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();

        int size = random.nextInt((int) (this.categoryRepo.count() - 1)) + 1;

        for (int i = 0; i < size; i++) {
            categories.add(getRandomCategory());
        }

        return categories;
    }
    @Override
    public List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less) {
        List<Product> products = this.productRepo
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(more,less);

        List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();

        for (Product product : products) {
            ProductInRangeDto productInRangeDto = this.modelMapper.map(product, ProductInRangeDto.class);
            productInRangeDto.setSeller(String.format("%s %s",product.getSeller().getFirstName(),
                    product.getSeller().getLastName()));

            productInRangeDtos.add(productInRangeDto);
        }

        return productInRangeDtos;
    }
}
