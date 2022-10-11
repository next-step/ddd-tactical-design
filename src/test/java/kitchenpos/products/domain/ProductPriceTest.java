package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @DisplayName("상품 가격은 Null 일 수 없다.")
    @Test
    void nullException() {
        assertThatThrownBy(() -> new ProductPrice(null))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바르지 않은 상품 가격입니다.");
    }

    @DisplayName("상품의 가격은 0보다 작을 수 없다.")
    @ValueSource(strings = {"-1", "-10"})
    @ParameterizedTest
    void negativeException(BigDecimal negativePrice) {
        assertThatThrownBy(() -> new ProductPrice(negativePrice))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품 가격은 0보다 작을 수 없습니다.");
    }
}
