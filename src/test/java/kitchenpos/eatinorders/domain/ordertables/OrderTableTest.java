package kitchenpos.eatinorders.domain.ordertables;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableTest {

    @DisplayName("새로운 Clear상태의 OrderTable을 생성할 수 있다.")
    @Test
    void createNew() {
        final OrderTable orderTable = OrderTable.createNew(new OrderTableName("테이블1"));
        assertAll(
                () -> assertThat(orderTable).isNotNull(),
                () -> assertThat(orderTable.getId()).isNotNull(),
                () -> assertThat(orderTable.getName()).isEqualTo(new OrderTableName("테이블1")),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(NumberOfGuests.ZERO),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

    @DisplayName("Clear 상태의 OrderTable을 Sit 으로 변경할 수 있다.")
    @Test
    void sit() {
        final OrderTable orderTable = OrderTable.createNew(new OrderTableName("테이블1"));
        orderTable.sit();
        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("Sit 상태의 OrderTable을 Clear 할 수 있다.")
    @Test
    void clear() {
        final OrderTable orderTable = OrderTable.createNew(new OrderTableName("테이블1"));
        orderTable.sit();
        orderTable.clear();
        assertAll(
                () -> assertThat(orderTable.isOccupied()).isFalse(),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(NumberOfGuests.ZERO)
        );
    }

    @DisplayName("OrderTable의 NumberOfGuests를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final OrderTable orderTable = OrderTable.createNew(new OrderTableName("테이블1"));
        orderTable.sit();
        orderTable.changeNumberOfGuests(new NumberOfGuests(4));
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(4));
    }

    @DisplayName("Clear인 OrderTable은 NumberOfGuests를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsWhenClearTable() {
        final OrderTable orderTable = OrderTable.createNew(new OrderTableName("테이블1"));
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(new NumberOfGuests(4)))
                .isInstanceOf(IllegalStateException.class);
    }
}
