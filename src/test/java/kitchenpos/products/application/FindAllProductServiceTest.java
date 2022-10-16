package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

class FindAllProductServiceTest {
    private ProductRepository productRepository;
    private FindAllProductService findAllProductService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        findAllProductService = new FindAllProductService(productRepository);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = findAllProductService.findAll();
        assertThat(actual).hasSize(2);
    }
}
