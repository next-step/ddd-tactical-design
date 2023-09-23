package kitchenpos.orders.ordertables.domain;

import kitchenpos.orders.ordertables.domain.NumberOfGuest;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.exception.OrderTableErrorCode;
import kitchenpos.orders.ordertables.exception.OrderTableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 테이블")
class OrderTableTest {

    @DisplayName("[성공] 주문 테이블을 등록한다.")
    @Test
    void create() {
        OrderTable orderTable = new OrderTable("name");
        assertAll(
                () -> assertThat(orderTable.getNumberOfGuestValue()).isZero(),
                () -> assertThat(orderTable.getNameValue()).isEqualTo("name"),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

    @DisplayName("[성공] 주문 테이블의 방문한 손님 수를 바꾼다.")
    @Test
    void changeNumberOfGuests1() {
        OrderTable orderTable = new OrderTable("name");
        orderTable.sit();
        orderTable.changeNumberOfGuest(new NumberOfGuest(9));

        assertThat(orderTable.getNumberOfGuestValue()).isEqualTo(9);
    }

    @DisplayName("[실패] 빈 테이블은 방문한 손님수를 바꿀 수 없다.")
    @Test
    void changeNumberOfGuests2() {
        OrderTable orderTable = new OrderTable("name");

        assertThatThrownBy(
                () -> orderTable.changeNumberOfGuest(new NumberOfGuest(9)))
                .isInstanceOf(OrderTableException.class)
                .hasMessage(OrderTableErrorCode.NON_OCCUPIED_CANNOT_CHANGE_NUMBER_OF_GUESTS.getMessage());

    }

    @DisplayName("[성공] 주문 테이블을 사용하다.")
    @Test
    void sit() {
        OrderTable orderTable = new OrderTable("name");
        orderTable.sit();
        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("[성공] 주문 테이블을 치운다.")
    @Test
    void clear() {
        OrderTable orderTable = new OrderTable("name");

        orderTable.sit();
        orderTable.changeNumberOfGuest(new NumberOfGuest(9));
        orderTable.clear();

        assertAll(
                () -> assertThat(orderTable.getNumberOfGuestValue()).isZero(),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

}
