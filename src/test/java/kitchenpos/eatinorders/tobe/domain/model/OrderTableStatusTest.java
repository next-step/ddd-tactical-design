package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException.*;
import static org.assertj.core.api.Assertions.*;

class OrderTableStatusTest {

    @DisplayName("점유 테이블은 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        OrderTableStatus occupied = OrderTableStatus.ofEmpty().occupy();

        assertThatNoException()
                .isThrownBy(() -> occupied.changeNumberOfGuests(2));
    }

    @DisplayName("손님 수를 음수로 변경할 수 없다.")
    @Test
    void changeNumberOfGuests_Exception() {
        OrderTableStatus status = OrderTableStatus.ofEmpty();
        status.occupy();

        assertThatThrownBy(() -> status.changeNumberOfGuests(-2))
                .isInstanceOf(IllegalOrderTableStatusException.class)
                .hasMessage(NEGATIVE_NUMBER_OF_GUESTS);
    }

    @DisplayName("비점유 테이블의 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuests_Exception2() {
        OrderTableStatus status = OrderTableStatus.ofEmpty();

        assertThatThrownBy(() -> status.changeNumberOfGuests(2))
                .isInstanceOf(IllegalOrderTableStatusException.class)
                .hasMessage(UNOCCUPIED);
    }

    @DisplayName("빈 테이블을 점유할 수 있다.")
    @Test
    void occupy() {
        OrderTableStatus empty = OrderTableStatus.ofEmpty();

        assertThatNoException()
                .isThrownBy(empty::occupy);
    }

    @DisplayName("점유 테이블을 다시 점유할 수 없다.")
    @Test
    void occupy_Exception() {
        OrderTableStatus occupied = OrderTableStatus.ofEmpty().occupy();

        assertThatThrownBy(() -> occupied.occupy())
                .isInstanceOf(IllegalOrderTableStatusException.class)
                .hasMessage(ALREADY_OCCUPIED);
    }

    @DisplayName("동등성 비교")
    @Test
    void equals() {
        OrderTableStatus status1 = OrderTableStatus.ofEmpty().occupy().changeNumberOfGuests(2);
        OrderTableStatus status2 = OrderTableStatus.ofEmpty().occupy().changeNumberOfGuests(2);

        assertThat(status1).isEqualTo(status2);
    }
}
