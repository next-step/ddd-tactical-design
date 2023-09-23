package kitchenpos.eatinorders.domain.ordertables;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableNameTest {

    @DisplayName("OrderTable name을 생성할 수 있다.")
    @Test
    void create() {
        final String name = "1번";
        final OrderTableName orderTableName = new OrderTableName(name);
        assertAll(
                () -> assertNotNull(orderTableName),
                () -> assertEquals(name, orderTableName.getValue())
        );
    }

    @DisplayName("Order Table Name 값이 비어있으면 생성할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createWithInvalidName(String name) {
        assertThatThrownBy(() -> new OrderTableName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
