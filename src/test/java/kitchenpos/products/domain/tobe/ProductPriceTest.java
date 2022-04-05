package kitchenpos.products.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ProductPriceTest {

    @DisplayName("상품의 가격은 0원 이상이어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1000", "-2000"})
    @NullSource
    void positive_price(BigDecimal value) {
        assertThatCode(() -> new ProductPrice(value)).isNotNull();
    }

    @DisplayName("가격을 곱한 값을 리턴한다.")
    @Test
    void multiply() {
        final ProductPrice productPrice = ProductPrice.valueOf(1000);
        final BigDecimal other = BigDecimal.TEN;

        ProductPrice actual = productPrice.multiply(other);

        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(10000));
    }
}
