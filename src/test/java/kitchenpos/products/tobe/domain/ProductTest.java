package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @DisplayName("상품 가격을 변경한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "1000"})
    void changePrice(BigDecimal price) {
        // given
        var product = new Product();
        var productPrice = new ProductPrice(price);

        // when
        product.changePrice(productPrice);

        // then
        assertThat(product.getPrice()).isEqualTo(productPrice);
    }
}
