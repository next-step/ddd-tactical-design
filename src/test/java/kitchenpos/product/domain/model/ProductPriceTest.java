package kitchenpos.product.domain.model;

import kitchenpos.product.domain.exception.ProductPriceValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {

    @DisplayName("`Product Price`를 생성할 수 있다")
    @Test
    void create() {
        // given
        final BigDecimal price = BigDecimal.valueOf(1000);

        // when
        final ProductPrice productPrice = ProductPrice.of(price);

        // then
        assertThat(productPrice.isSamePrice(price)).isTrue();
    }

    @DisplayName("`Product Price`는 0원 이상 입력하여야 한다")
    @Test
    void createWithNegativePrice() {
        // given
        final BigDecimal price = BigDecimal.valueOf(-1);

        // when
        final Throwable thrown = catchThrowable(() -> ProductPrice.of(price));

        // then
        assertThat(thrown).isInstanceOf(ProductPriceValidationException.class);
    }

    @DisplayName("`Product Price`는 반드시 입력해야한다")
    @Test
    void createWithNullPrice() {
        // when
        final Throwable thrown = catchThrowable(() -> ProductPrice.of(null));

        // then
        assertThat(thrown).isInstanceOf(ProductPriceValidationException.class);
    }

    @DisplayName("isSamePrice 메소드 테스트")
    @ParameterizedTest
    @CsvSource(value = {"1000, 1000, true", "1000, 2000, false", "1.10, 1.1, true"})
    void testIsSamePrice(BigDecimal actualPrice, BigDecimal expectedPrice, boolean expected) {
        // given
        final ProductPrice productPrice = ProductPrice.of(actualPrice);

        // when
        final boolean isSamePrice = productPrice.isSamePrice(expectedPrice);

        // then
        assertThat(isSamePrice).isEqualTo(expected);
    }

    @DisplayName("isSamePrice 메소드에 null 값을 넣으면 false를 반환한다")
    @Test
    void testIsSamePriceWhenNullValue() {
        // given
        final ProductPrice productPrice = ProductPrice.of(BigDecimal.ZERO);

        // when
        final boolean isSamePrice = productPrice.isSamePrice(null);

        // then
        assertThat(isSamePrice).isFalse();
    }
}