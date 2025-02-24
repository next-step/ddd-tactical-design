package kitchenpos.order.eatinorder.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableTest {

    @Test
    @DisplayName("주문 테이블이 비어있을 때, 테이블을 점유하는 손님 수를 변경하면 예외를 던진다.")
    void change_number_of_guests_empty_order_table_exception() {
        // given
        OrderTable orderTable = new OrderTable(new OrderTableName("1번 테이블"));

        // when // then
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문 테이블이 비어있습니다!");
    }

    @Test
    @DisplayName("주문 테이블을 점유하는 손님 수를 음수로 변경하면 예외를 던진다.")
    void change_number_of_guests_guest_number_exception() {
        // given
        OrderTable orderTable = new OrderTable(new OrderTableName("1번 테이블"), 1, true);

        // when // then
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("손님 수가 음수일 수 없습니다!");
    }

    @Test
    @DisplayName("주문 테이블을 손님이 점유하고 있을 때, 손님 수를 변경할 수 있다.")
    void change_number_of_guests_success() {
        // given
        OrderTable orderTable = new OrderTable(new OrderTableName("1번 테이블"), 1, true);

        // when
        orderTable.changeNumberOfGuests(3);

        // then
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(3);
    }
}
