package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @DisplayName("상품의 가격은 0 보다 작을 수 없다.")
    @Test
    void negativePrice() {
        assertThatThrownBy(() -> new ProductPrice(BigDecimal.valueOf(-100)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("상품의 가격은 입력하지 않을 수 없다.")
    @ParameterizedTest
    @NullSource
    void nullPrice(BigDecimal price) {
        assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("상품의 가격과 곱한 결과를 구할 수 있다.")
    @Test
    void multiplyPrice() {
        ProductPrice price = new ProductPrice(BigDecimal.valueOf(20000));

        assertThat(price.multiplyPrice(BigDecimal.valueOf(5))).isEqualTo(BigDecimal.valueOf(100000));
    }
}
