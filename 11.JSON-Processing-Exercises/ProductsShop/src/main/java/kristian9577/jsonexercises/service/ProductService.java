package kristian9577.jsonexercises.service;

import kristian9577.jsonexercises.domain.dto.ProductInRangeDto;
import kristian9577.jsonexercises.domain.dto.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less);
}
