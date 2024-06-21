package kitchenpos.products.tobe;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class MoneyTest {


    @Test
    @DisplayName("금액은 null이 될 수 없습니다.")
    public void createFailedWhenPriceIsZeroTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Money(null);
        });
    }

    @Test
    @DisplayName("금액은 0보다 작을 수 없습니다.")
    public void createFailedWhenPriceIsLessThan_zeroTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Money(BigDecimal.valueOf(-1));
        });
    }


}