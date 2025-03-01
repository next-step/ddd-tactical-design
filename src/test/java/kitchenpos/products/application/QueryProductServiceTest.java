package kitchenpos.products.application;

import kitchenpos.fixture.ProductFixtures;
import kitchenpos.core.products.application.QueryProductService;
import kitchenpos.core.products.tobe.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kitchenpos.fixture.Fixtures.menu;
import static kitchenpos.fixture.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueryProductServiceTest {
    private TobeProductRepository productRepository;
    private QueryProductService sut;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        sut = new QueryProductService(productRepository);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(ProductFixtures.product("후라이드", 16_000L));
        productRepository.save(ProductFixtures.product("양념치킨", 16_000L));
        final List<Product> actual = sut.findProducts();
        assertThat(actual).hasSize(2);
    }

}
