package kitchenpos.eatinorders.domain.ordertables;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class OrderTableNameTest {

    @DisplayName("주문 테이블 이름을 생성할 수 있다.")
    @Test
    void create() {
        final String name = "1번";
        final OrderTableName orderTableName = new OrderTableName(name);
        assertAll(
                () -> assertNotNull(orderTableName),
                () -> assertEquals(name, orderTableName.getValue())
        );
    }

    @DisplayName("주문 테이블 값이 올바르지 않으면 생성할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createWithInvalidName(String name) {
        assertThrows(IllegalArgumentException.class, () -> new OrderTableName(name));
    }
}
