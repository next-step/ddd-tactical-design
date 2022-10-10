package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.global.vo.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 테이블")
class OrderTableTest {

    @DisplayName("주문 테이블 생성")
    @Test
    void createOrderTable() {
        OrderTable orderTable = new OrderTable(new DisplayedName("1번"), new NumberOfGuests(0), false);

        assertAll(
                () -> assertThat(orderTable.getName()).isEqualTo(new DisplayedName("1번")),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(0))
        );
    }

    @DisplayName("주문 테이블 clear")
    @Test
    void orderTableClear() {
        OrderTable orderTable = new OrderTable(new DisplayedName("1번"), new NumberOfGuests(2), true);
        orderTable.tableClear();

        assertAll(
                () -> assertThat(orderTable.getName()).isEqualTo(new DisplayedName("1번")),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(0)),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블 손님수 변경")
    @Test
    void changeOrderTableNumberOfGuests() {
        OrderTable orderTable = new OrderTable(new DisplayedName("1번"), new NumberOfGuests(2), true);
        orderTable.chageNumberOfGuests(new NumberOfGuests(4));

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(4));
    }

    @DisplayName("빈 테이블에서 손님수 변경하면 에러")
    @Test
    void changeOrderTableNumberOfGuestsEmptyTable() {
        OrderTable orderTable = new OrderTable(new DisplayedName("1번"), new NumberOfGuests(2), false);

        assertThatThrownBy(() -> orderTable.chageNumberOfGuests(new NumberOfGuests(4))).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블 앉기")
    @Test
    void orderTableSit() {
        OrderTable orderTable = new OrderTable(new DisplayedName("1번"), new NumberOfGuests(2), false);
        orderTable.sit();

        assertThat(orderTable.isOccupied()).isTrue();
    }
}
