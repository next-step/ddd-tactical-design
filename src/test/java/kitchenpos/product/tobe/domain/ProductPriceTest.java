package kitchenpos.product.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @DisplayName("상품 가격 객체를 생성한다")
    @ParameterizedTest
    @ValueSource(longs = {0, 1_000, 10_000})
    void testInitProductPrice(long value) {

        // when // then
        assertDoesNotThrow(() -> new ProductPrice(BigDecimal.valueOf(value)));
    }

    @DisplayName("상품 가격이 음수라면 상품 가격 객체를 생성할 수 없다")
    @ParameterizedTest
    @ValueSource(longs = {-1_000, -10_000})
    void testInitProductPriceIfNotValidPrice(long value) {
        // when // then
        assertThatThrownBy(() -> new ProductPrice(BigDecimal.valueOf(value)))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 가격이 null이면 상품 가격 객체를 생성할 수 없다")
    @Test
    void testInitProductPriceIfPriceIsNull() {
        // when // then
        assertThatThrownBy(() -> new ProductPrice(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
