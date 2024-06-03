package kitchenpos.products.tobe.application;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductQueryHandlerTest {

    private ProductRepository productRepository;
    private ProductQueryHandler productQueryHandler;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productQueryHandler = new ProductQueryHandler(productRepository);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = productQueryHandler.findAll();
        assertThat(actual).hasSize(2);
    }
}
