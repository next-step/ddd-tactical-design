package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @DisplayName("상품 가격을 생성할 수 있다.")
    @Test
    void create01() {
        assertThat(new ProductPrice(BigDecimal.TEN)).isEqualTo(new ProductPrice(BigDecimal.TEN));
    }

    @DisplayName("상품 가격은 비어 있을 수 없다.")
    @Test
    void create02() {
        assertThatThrownBy(() -> new ProductPrice(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 필수로 입력 해야 합니다.");
    }

    @DisplayName("상품 가격은 0미만일 수 없다.")
    @Test
    void create03() {
        assertThatThrownBy(() -> new ProductPrice(BigDecimal.valueOf(-1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 가격은 0원 이상이어야 합니다.");
    }
}
