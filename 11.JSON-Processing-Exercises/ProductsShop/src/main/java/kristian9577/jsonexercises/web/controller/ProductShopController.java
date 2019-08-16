package kristian9577.jsonexercises.web.controller;

import com.google.gson.Gson;
import kristian9577.jsonexercises.domain.dto.CategorySeedDto;
import kristian9577.jsonexercises.domain.dto.ProductInRangeDto;
import kristian9577.jsonexercises.domain.dto.ProductSeedDto;
import kristian9577.jsonexercises.domain.dto.UserSeedDto;
import kristian9577.jsonexercises.service.CategoryService;
import kristian9577.jsonexercises.service.ProductService;
import kristian9577.jsonexercises.service.UserService;
import kristian9577.jsonexercises.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {

    private final static String USER_PATH = "C:\\PROJECTS\\JSON-Processing-Exercises\\ProductsShop\\src\\main\\resources\\json\\users.json";
    private final static String CATEGORY_PATH = "C:\\PROJECTS\\JSON-Processing-Exercises\\ProductsShop\\src\\main\\resources\\json\\categories.json";
    private final static String PRODUCT_PATH = "C:\\PROJECTS\\JSON-Processing-Exercises\\ProductsShop\\src\\main\\resources\\json\\products.json";

    private final Gson gson;
    private final FileUtil fileUtil;

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductShopController(Gson gson, FileUtil fileUtil, UserService userService, CategoryService categoryService, ProductService productService) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

//        seedUsers();
//        seedCategories();
//        seedProducts();
        productsInRange();
    }

    private void seedUsers() throws IOException {
        String content = this.fileUtil.fileContent(USER_PATH);

        UserSeedDto[] userSeedDtos = this.gson.fromJson(content, UserSeedDto[].class);
        this.userService.seedUsers(userSeedDtos);
    }

    private void seedCategories() throws IOException {
        String content = this.fileUtil.fileContent(CATEGORY_PATH);

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(content, CategorySeedDto[].class);
        this.categoryService.seedCategories(categorySeedDtos);

    }

    private void seedProducts() throws IOException {
        String content = this.fileUtil.fileContent(PRODUCT_PATH);

        ProductSeedDto[] productSeedDtos = this.gson.fromJson(content, ProductSeedDto[].class);
        this.productService.seedProducts(productSeedDtos);
    }
    private void productsInRange(){
        List<ProductInRangeDto> products = this.productService
                .productsInRange(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));

        String productsInRangeJson = this.gson.toJson(products);

        System.out.println(productsInRangeJson);
    }
}
