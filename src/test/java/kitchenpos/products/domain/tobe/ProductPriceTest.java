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

    @DisplayName("가격을 입력받아 비교가격과 동일하면 true를 리턴한다.")
    @Test
    void is_same_price() {
        final ProductPrice productPrice = ProductPrice.valueOf(BigDecimal.TEN);
        final BigDecimal other = BigDecimal.TEN;

        assertThat(productPrice.isSame(other)).isTrue();
    }

    @DisplayName("본래 가격을 입력받아 비교가격보다 적다면 true를 리턴한다.")
    @Test
    void is_less_price() {
        final ProductPrice productPrice = ProductPrice.valueOf(BigDecimal.TEN);
        final BigDecimal other = BigDecimal.valueOf(1000);

        assertThat(productPrice.isLessThan(other)).isTrue();
    }

    @DisplayName("본래 가격이 비교가격보다 더 크다면 true를 리턴한다.")
    @Test
    void is_bigger_price() {
        final ProductPrice productPrice = ProductPrice.valueOf(BigDecimal.valueOf(2000));
        final BigDecimal other = BigDecimal.valueOf(1000);

        assertThat(productPrice.isMoreThan(other)).isTrue();
    }
}
