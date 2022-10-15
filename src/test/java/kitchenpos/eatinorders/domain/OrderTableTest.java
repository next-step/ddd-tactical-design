package kitchenpos.eatinorders.domain;

import static kitchenpos.eatinorders.OrderFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableTest {

    @DisplayName("테이블이 착석 상태가 아닐 경우 손님 수를 변경할 수 없다.")
    @Test
    void notOccupiedException() {
        OrderTable orderTable = orderTable(false, 0);
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(5))
            .isExactlyInstanceOf(IllegalStateException.class)
            .hasMessage("착석중이지 않아 손님 수를 변경할 수 없습니다.");
    }
}
