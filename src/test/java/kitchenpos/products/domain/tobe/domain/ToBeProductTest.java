package kitchenpos.products.domain.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToBeProductTest {

    private ToBeProduct product;
    private BigDecimal price;

    @BeforeEach
    void setUp() {
        price = BigDecimal.valueOf(30_000L);
        product = new ToBeProduct("돈가스", price, false);
    }

    @DisplayName("가격을 변경한다")
    @Test
    void changePrice() {
        product.changePrice(price);
        assertThat(product.isSamePrice(price))
            .isTrue();
    }

    @DisplayName("상품 가격이 입력 금액보다 크다")
    @Test
    void isGreaterThanPrice() {
        assertThat(product.isGreaterThanPrice(BigDecimal.valueOf(29_999L)))
            .isTrue();
    }
}
