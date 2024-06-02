package kitchenpos.eatinorders.todo.domain.ordertable;

import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.todo.domain.ordertable.OrderTable.OCCUPIED;
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
                () -> assertThat(actual.isOccupied()).isFalse()
        );
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
        NumberOfGuests numberOfGuests = NumberOfGuests.from(4);
        OrderTable orderTable = OrderTable.from(주문테이블_이름, NumberOfGuests.ZERO_NUMBER_OF_GUESTS, OCCUPIED);

        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(numberOfGuests))
                .isInstanceOf(KitchenPosIllegalStateException.class);
    }
}
