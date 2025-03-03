package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @DisplayName("상품 가격이 0보다 작을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "-1000"})
    void createProductPriceWithNegativePrice() {
        // given
        var price = BigDecimal.valueOf(-1);

        // when, then
        assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 가격을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "1000"})
    void createProductPrice(String price) {
        // given
        var productPrice = new ProductPrice(new BigDecimal(price));

        // then
        assertThat(productPrice.value()).isEqualTo(new BigDecimal(price));
    }
}
