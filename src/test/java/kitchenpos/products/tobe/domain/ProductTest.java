package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    @DisplayName("같은 값의 객체 비교")
    void compareProductByValueEquals() {
        // give
        Product friedChicken = new Product(1L, "후라이드 치킨", BigDecimal.valueOf(18_000));
        Product friedChickenAgain = new Product(1L, "후라이드 치킨", BigDecimal.valueOf(18_000));

        // when then
        assertThat(friedChicken.equals(friedChickenAgain)).isTrue();
    }
}