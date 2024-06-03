package kitchenpos.eatinorders.todo.domain.ordertables;

import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.todo.domain.ordertables.OrderTable.OCCUPIED;
import static kitchenpos.eatinorders.todo.domain.ordertables.OrderTable.UNOCCUPIED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문테이블")
class OrderTableTest {
    private static final OrderTableName 주문테이블_이름 = OrderTableName.from("주문테이블 이름");

    @DisplayName("[성공] 주문 테이블을 생성한다.")
    @Test
    void create() {
        OrderTable actual = OrderTable.from(주문테이블_이름);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.name()).isEqualTo("주문테이블 이름"),
                () -> assertThat(actual.getNumberOfGuests()).isZero(),
                () -> assertThat(actual.isOccupied()).isEqualTo(UNOCCUPIED)
        );
    }

    @DisplayName("[성공] 주문 테이블에 매장고객이 착석한다.")
    @Test
    void sit() {
        OrderTable orderTable = OrderTable.from(주문테이블_이름);;

        orderTable.sit();

        assertThat(orderTable.isOccupied()).isEqualTo(OCCUPIED);
    }

    @DisplayName("[성공] 주문 테이블이 초기화된다.")
    @Test
    void clear() {
        OrderTable orderTable = OrderTable.from(주문테이블_이름, NumberOfGuests.from(4), OCCUPIED);

        orderTable.clear();

        assertAll(
                () -> assertThat(orderTable.isOccupied()).isEqualTo(UNOCCUPIED),
                () -> assertThat(orderTable.getNumberOfGuests()).isZero()
        );
    }

    @DisplayName("[성공] 주문 테이블에 유효한 주문만 포함되면 주문테이블은 초기화된다.")
    @Test
    void clear2() {
        OrderTable orderTable = OrderTable.from(주문테이블_이름, NumberOfGuests.from(4), OCCUPIED);

        orderTable.clear(containsInvalidOrder -> false);

        assertAll(
                () -> assertThat(orderTable.isOccupied()).isEqualTo(UNOCCUPIED),
                () -> assertThat(orderTable.getNumberOfGuests()).isZero()
        );
    }


    @DisplayName("[실패] 주문 테이블에 유효하지 않은 주문이 포함되면 주문테이블은 초기화 될 수 없다.")
    @Test
    void fail_clear() {
        OrderTable orderTable = OrderTable.from(주문테이블_이름, NumberOfGuests.from(4), OCCUPIED);;;

        assertThatThrownBy(() -> orderTable.clear(containsInvalidOrder -> true))
                .isInstanceOf(KitchenPosIllegalStateException.class);
    }

    @DisplayName("[성공] 주문 테이블 착석한 고객의 수를 변경한다")
    @Test
    void changeNumberOfGuests() {
        NumberOfGuests numberOfGuests = NumberOfGuests.from(4);
        OrderTable orderTable = OrderTable.from(주문테이블_이름);
        orderTable.sit();

        orderTable.changeNumberOfGuests(numberOfGuests);

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("[실패] 주문 테이블 착석한 고객의 수를 변경할 때, 테이블 상태는 점유 상태여야 한다.")
    @Test
    void fail_changeNumberOfGuests() {
        NumberOfGuests newNumberOfGuests = NumberOfGuests.from(4);
        OrderTable orderTable = OrderTable.from(주문테이블_이름, NumberOfGuests.ZERO_NUMBER_OF_GUESTS, UNOCCUPIED);

        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(newNumberOfGuests))
                .isInstanceOf(KitchenPosIllegalStateException.class);
    }
}
