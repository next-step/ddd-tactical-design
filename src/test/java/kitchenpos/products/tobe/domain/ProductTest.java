package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Geonguk Han
 * @since 2020-03-07
 */
public class ProductTest {

    @Test
    void product_validate_price_success() {
        final Product product = new Product(1L, "양념치킨", BigDecimal.valueOf(12_000));
        product.validatePrice();
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -10_000L})
    void product_validate_price_fail(final Long price) {
        final Product product = new Product(1L, "양념치킨", BigDecimal.valueOf(price));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> product.validatePrice());
    }
}
