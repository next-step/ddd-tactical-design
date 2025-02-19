package kitchenpos.product.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {

    @DisplayName("가격은 필수이다.")
    @NullSource
    @ParameterizedTest
    void price_is_null(Long price) {
        // given
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ProductPrice(price));
        // then
        assertEquals("상품 가격은 필수값입니다.", exception.getMessage());
    }
    @DisplayName("가격은 0보다 작을 수 없다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void price_is_negative(Long price) {
        // given
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ProductPrice(price));
        // then
        assertEquals("상품 가격은 0보다 작을 수 없습니다.", exception.getMessage());
    }
}