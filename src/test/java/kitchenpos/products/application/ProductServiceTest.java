package kitchenpos.products.application;

import kitchenpos.products.infrastructure.dao.ProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.dto.ProductRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.products.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        // given
        final ProductRequestDto expected = friedChickenRequest();

        // when
        final Product actual = productService.create(expected);

        // then
        assertProduct(expected, actual);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        // given
        final ProductRequestDto expected = friedChickenRequest();
        expected.setPrice(price);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> productService.create(expected));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    @Transactional
    void list() {
        // given
        final Product friedChicken = productRepository.save(friedChicken());
        final Product seasonedChicken = productRepository.save(seasonedChicken());

        // when
        final List<Product> actual = productService.list();

        // then
        assertThat(actual).containsAll(Arrays.asList(friedChicken, seasonedChicken));
    }

    private void assertProduct(final ProductRequestDto expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}
