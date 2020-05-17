package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @DisplayName("상품을 가격은 0원 미만으로 생성시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1000", "-2000", "-10000", "-18000"})
    void given_negative_price_when_create_product_then_throw_exception(BigDecimal price) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Product("치킨", price));
    }

}
