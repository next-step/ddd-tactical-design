package kitchenpos.products.domain.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToBeProductTest {

    private BigDecimal price;

    @BeforeEach
    void setUp() {
        price = BigDecimal.valueOf(30_000L);
    }

    @Test
    void changePrice() {

        ToBeProduct product = new ToBeProduct("돈가스", price);
        product.changePrice(price);
        assertThat(product.isSamePrice(price))
            .isTrue();
    }

    @Test
    void isGreaterThanPrice() {
        ToBeProduct product = new ToBeProduct("돈가스", price);
        assertThat(product.isGreaterThanPrice(BigDecimal.valueOf(29_999L)))
            .isTrue();
    }
}