package kitchenpos.shared.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductPriceTest {

    @DisplayName("가격으로 상품 가격을 생성할 수 있다.")
    @Test
    void create() {
        // given
        BigDecimal price = new BigDecimal(100);

        // when
        ProductPrice productPrice = new ProductPrice(price);

        // then
        assertThat(productPrice.getValue()).isEqualTo(price);
    }

    @DisplayName("최소 가격 미만으로 상품 가격을 생성 시, 예외가 발생한다.")
    @Test
    void createWithNegativePrice() {
        // given
        BigDecimal price = new BigDecimal(ProductPrice.MINIMUM_PRICE - 1);
        // when then
        assertThatThrownBy(() -> new ProductPrice(price))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ProductPrice.ERROR_MESSAGE_PRICE);
    }
}
