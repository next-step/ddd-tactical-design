package kitchenpos.products.tobe.domain.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductTest {

    @DisplayName("상품의 가격이 올바르지 상품을 만들 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void create(final BigDecimal price) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Product(1L,"양념치킨", price));
    }
}