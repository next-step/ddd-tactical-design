package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Geonguk Han
 * @since 2020-03-07
 */
public class ProductTest {

    @Test
    @DisplayName("상품의 가격이 정상인 경우")
    void product_validate_price_success() {
        final BigDecimal expectedPrice = BigDecimal.valueOf(10_000);
        final Price price = new Price(expectedPrice);
        assertThat(price.getPrice()).isEqualTo(expectedPrice);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -10_000L})
    @DisplayName("상품 가격이 비정상적인 경우")
    void product_validate_price_fail(final Long price) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Product("양념치킨", BigDecimal.valueOf(price)));
    }
}
