package kitchenpos.products.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ProductTest {

    @DisplayName("메뉴에 포함된 상품의 금액과 수량을 곱한다")
    @ParameterizedTest
    @ValueSource(longs = 2L)
    void multiply(final Long quantity) {
        Product product = product();
        BigDecimal actual = product.multiply(quantity);
        assertThat(actual).isEqualTo(new BigDecimal(32_000L));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        Product product = product();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> product.changePrice(price));
    }
}
