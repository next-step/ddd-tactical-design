package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceVoTests {
    @DisplayName("값을 0원 이상으로 상품 가격 책정 시 성공")
    @ParameterizedTest
    @ValueSource(ints = 16000)
    public void valueOfProductPriceWithValidValue(final int price) {
        ProductPrice productPrice = ProductPrice.valueOf(price);

        assertThat(productPrice.checkProductPriceValue()).isEqualTo(BigDecimal.valueOf(price));
    }

    @DisplayName("값을 0원 미안으로 상품 가격 책정 시 성공")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2})
    public void valueOfProductPriceWithInValidValue(final int price) {
        assertThatThrownBy(() -> {
            ProductPrice.valueOf(price);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
