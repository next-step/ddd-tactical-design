package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @Test
    @DisplayName("상품 가격을 생성할 수 있다.")
    void create() {
        // given
        BigDecimal price = BigDecimal.valueOf(10_000L);

        // when
        ProductPrice actual = new ProductPrice(price);

        // then
        assertThat(actual).isEqualTo(new ProductPrice(price));
    }

    @Test
    @DisplayName("상품의 가격은 0원 이상이어야 한다")
    void lessThanZero() {
        assertThatThrownBy(() -> new ProductPrice(BigDecimal.valueOf(-1_000L)))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 가격은 빈 값이 될 수 없다")
    void emptyValue() {
        assertThatThrownBy(() -> new ProductPrice(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
