package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderTableTest {
    /**
     * TODO
     * [] 완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.
     **/

    @DisplayName("빈 주문 테이블을 생성할 수 있다.")
    @Test
    void emptyOrderTable() {
        assertDoesNotThrow(() -> OrderTable.empty("1번"));
    }

    @DisplayName("주문 테이블을 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> OrderTable.of("1번", 7, false));
    }

    @DisplayName("주문 테이블의 이름이 없으면 생성할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void nullAndEmptyName(String name) {
        assertThatThrownBy(() -> OrderTable.empty(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 테이블 이름 입니다");
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void use() {
        OrderTable orderTable = emptyTable();
        orderTable.use();

        assertThat(orderTable.isEmpty()).isFalse();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void empty() {
        OrderTable orderTable = emptyTable();
        orderTable.empty();

        assertThat(orderTable.isEmpty()).isTrue();
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        int numberOfGuests = 7;
        OrderTable orderTable = OrderTable.of("1번", 1, false);

        orderTable.changeNumberOfGuests(numberOfGuests);

        assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(numberOfGuests));
    }

    @DisplayName("방문한 손님 수를 0 보다 작은 값으로 변경할 수 없다.")
    @Test
    void invalidNumberOfGuests() {
        OrderTable orderTable = OrderTable.of("1번", 1, false);

        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 방문한 손님 수 입니다.");
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void invalidEmptyTable() {
        OrderTable orderTable = OrderTable.empty("1번");
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("빈 테이블 입니다.");
    }

    private OrderTable emptyTable() {
        return OrderTable.empty("1번");
    }
}
