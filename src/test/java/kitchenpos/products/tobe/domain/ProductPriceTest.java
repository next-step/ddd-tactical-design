package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductPriceTest {

    @Test
    @DisplayName("상품의 가격을 생성 할 수 있다.")
    void test_1() {
        // given
        final ProductPrice productPrice = ProductPrice.of(BigDecimal.valueOf(1000));

        // then
        assertNotNull(productPrice);
        assertEquals(BigDecimal.valueOf(1000), productPrice.price());
    }

    @Test
    @DisplayName("상품의 가격은 0 이상이어야 한다.")
    void test_2() {

        BigDecimal price = BigDecimal.valueOf(-1);

        // when & then
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> ProductPrice.of(price));
        assertEquals("가격은 0 이상이어야 합니다.", exception.getMessage());
    }
}
