package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @DisplayName("가격은 0보다 작을 수 없다.")
    @Test
    void createPrice() {
        // given
        final long price = -1L;

        // when then
        assertThrows(IllegalArgumentException.class, () -> new Price(price));
    }


    @DisplayName("가격은 0 이상이어야 한다.")
    @Test
    void createPrice3() {
        // given
        final long price = 0L;

        // when
        final Price actual = new Price(price);

        // then
        assertNotNull(actual);
    }
}
