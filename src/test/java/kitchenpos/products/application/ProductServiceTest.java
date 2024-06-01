package kitchenpos.products.application;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.application.ChangePrice;
import kitchenpos.products.tobe.domain.application.CreateProduct;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductCreateDto;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        CreateProduct dummyCreateProduct = (request) -> new Product();
        ChangePrice dummyChangeProduct = (uuid, request) -> new Product();
        productService = new ProductService(productRepository, dummyCreateProduct, dummyChangeProduct);
    }


    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private ProductCreateDto createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private ProductCreateDto createProductRequest(final String name, final BigDecimal price) {
        return new ProductCreateDto(name, price);
    }

    private ProductPriceChangeDto changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private ProductPriceChangeDto changePriceRequest(final BigDecimal price) {
        return new ProductPriceChangeDto(price);
    }
}
