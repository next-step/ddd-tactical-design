package kitchenpos.common.values;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;
import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


class QuantityTest {

    @DisplayName("[정상] 가격을 생성합니다.")
    @Test
    void create() {
        final Quantity quantity = new Quantity(1000L);
        assertAll(
                () -> assertNotNull(quantity),
                () -> assertTrue(quantity.equalValue(1000L))
        );
    }

    @DisplayName("[예외] 음수로 가격을 생성할 수 없습니다.")
    @Test
    void create_exception1() {
        assertThrows(BAD_REQUEST, () -> new Quantity(-1L));
    }

}
