package kitchenpos.domain.order.tobe.domain;

import kitchenpos.domain.support.Guest;
import kitchenpos.domain.support.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderTableTest {

    @DisplayName("주문 테이블을 생성한다")
    @Test
    void constructor() {
        OrderTable orderTable = new OrderTable(new Name("주문테이블A"), new Guest(1));
        assertThat(orderTable.name()).isEqualTo("주문테이블A");
        assertThat(orderTable.countOfGuest()).isEqualTo(1);
        assertThat(orderTable.isOccupied()).isFalse();
    }

    @DisplayName("주문 테이블의 이름이 Null 혹은 빈 값이면 생성을 실패한다")
    @NullAndEmptySource
    @ParameterizedTest
    void constructor_name_fail(String name) {
        assertThatIllegalArgumentException().isThrownBy(() -> new OrderTable(new Name(name), new Guest(1)));
    }

    @DisplayName("주문 테이블의 손님 수가 음수이면 생성을 실패한다")
    @Test
    void constructor_guest_count_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new OrderTable(new Name("주문테이블A"), new Guest(-1)));
    }

    @DisplayName("주문 테이블을 Occupied Order Table로 변경한다")
    @Test
    void occupy() {
        OrderTable orderTable = new OrderTable(new Name("주문테이블A"), new Guest(1));

        orderTable.occupy();

        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("주문 테이블을 Cleared Order Table로 변경한다")
    @Test
    void clear() {
        OrderTable orderTable = new OrderTable(new Name("주문테이블A"), new Guest(1));

        orderTable.clear();

        assertThat(orderTable.isOccupied()).isFalse();
    }

    @DisplayName("주문 테이블 Occupied Order Table이면, 손님 수를 변경할 수 있다")
    @Test
    void changeCountOfGuest() {
        OrderTable orderTable = new OrderTable(new Name("주문테이블A"), new Guest(1));
        orderTable.occupy();

        orderTable.changeCountOfGuest(10);

        assertThat(orderTable.countOfGuest()).isEqualTo(10);
    }

    @DisplayName("주문 테이블 손님 수 변경 시, Cleared Order Table이면 변경을 실패한다")
    @Test
    void changeCountOfGuest_cleared_fail() {
        OrderTable orderTable = new OrderTable(new Name("주문테이블A"), new Guest(1));

        assertThatIllegalArgumentException().isThrownBy(() -> orderTable.changeCountOfGuest(10));
    }

    @DisplayName("주문 테이블 손님 수 변경 시, 손님 수가 음수이면 변경을 실패한다")
    @Test
    void changeCountOfGuest_count_fail() {
        OrderTable orderTable = new OrderTable(new Name("주문테이블A"), new Guest(1));
        assertThatIllegalArgumentException().isThrownBy(() -> orderTable.changeCountOfGuest(-1));
    }
}
