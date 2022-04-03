package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @ParameterizedTest
    @DisplayName("상품 가격은 0원 이상이어야 한다.")
    @ValueSource(longs = {0L, 1000L})
    void productPrice(long price) {
        // given when
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));

        // then
        assertThat(productPrice).isEqualTo(new ProductPrice(BigDecimal.valueOf(price)));
    }

    @Test
    @DisplayName("상품 가격은 필수 항목이다.")
    void priceIsMandatory() {
        // given when then
        assertThatThrownBy(() -> new ProductPrice(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 가격은 필수항목입니다. 상품가격을 확인해 주세요.");
    }

    @Test
    @DisplayName("상품 가격은 음수일 수 없다.")
    void negativePrice() {
        // given when then
        assertThatThrownBy(() -> new ProductPrice(BigDecimal.valueOf(-1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 가격은 0 원 이상이어야 합니다. 상품가격을 확인해 주세요.");
    }
}
