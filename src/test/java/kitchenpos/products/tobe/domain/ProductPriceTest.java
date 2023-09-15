package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {

    @DisplayName("상품 가격을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "1000"})
    void create(BigDecimal price) {
        final ProductPrice productPrice = assertDoesNotThrow(() -> ProductPrice.from(price));
        assertAll(
                () -> assertNotNull(productPrice),
                () -> assertEquals(price, productPrice.getValue())
        );
    }

    @DisplayName("상품 가격은 null이 될 수 없다.")
    @ParameterizedTest
    @NullSource
    void createWithNullPrice(BigDecimal price) {
        assertThatThrownBy(() -> ProductPrice.from(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 가격은 음수가 될 수 없다.")
    @Test
    void createWithNegativePrice() {
        assertThatThrownBy(() -> ProductPrice.from(BigDecimal.valueOf(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 가격끼리 더할 수 있다.")
    @Test
    void add() {
        final ProductPrice productPrice = ProductPrice.from(BigDecimal.valueOf(1000L));
        final ProductPrice otherProductPrice = ProductPrice.from(BigDecimal.valueOf(2000L));
        final ProductPrice actual = productPrice.add(otherProductPrice);
        assertThat(actual).isEqualTo(ProductPrice.from(BigDecimal.valueOf(3000L)));
    }

    @DisplayName("상품 가격에 수량을 곱할 수 있다.")
    @Test
    void multiplyQuantity() {
        final ProductPrice productPrice = ProductPrice.from(BigDecimal.valueOf(1000L));
        final BigDecimal quantity = BigDecimal.valueOf(2L);
        final ProductPrice actual = productPrice.multiplyQuantity(quantity);
        assertThat(actual).isEqualTo(ProductPrice.from(BigDecimal.valueOf(2000L)));
    }
}
