package kitchenpos.product.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductPriceTest {

    @Test
    @DisplayName("가격이 null이라면 예외가 발생한다.")
    void test1() {
        Assertions.assertThatThrownBy(() -> new ProductPrice(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 음수라면 예외가 발생한다.")
    void test2() {
        Assertions.assertThatThrownBy(() -> new ProductPrice(BigDecimal.valueOf(-1000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격을 정상적으로 생성할 수 있다.")
    void test3() {
        BigDecimal validPrice = BigDecimal.valueOf(1000);
        ProductPrice productPrice = new ProductPrice(validPrice);

        Assertions.assertThat(productPrice).isNotNull();
        Assertions.assertThat(productPrice.getPrice()).isEqualTo(validPrice);
    }
}
