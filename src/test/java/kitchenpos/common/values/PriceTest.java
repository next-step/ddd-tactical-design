package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosExceptionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;
import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


class PriceTest {

    @Test
    void create() {
        final Price price = new Price(1000L);
        assertAll(
                () -> assertNotNull(price),
                () -> assertEquals(BigDecimal.valueOf(1000L), price.getValue())
        );
    }

    @Test
    void createWithNegative() {
        assertThrows(BAD_REQUEST, () -> new Price(-1L));
    }

}
