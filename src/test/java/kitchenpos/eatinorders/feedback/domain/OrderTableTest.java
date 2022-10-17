package kitchenpos.eatinorders.feedback.domain;

import kitchenpos.eatinorders.feedback.InMemoryEatInOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderTableTest {
    @DisplayName("주문 테이블을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new OrderTable("1번 테이블"));
    }

    @DisplayName("주문 테이블의 손님 수를 변경한다.")
    @Test
    void changeNumberOfGuest() {
        OrderTable orderTable = new OrderTable("1번 테이블");
        orderTable.use();

        orderTable.changeNumberOfGuest(10);

        assertThat(orderTable.getNumberOfGuest()).isEqualTo(10);
    }

    @DisplayName("손님 수를 음수로 변경할 수 없다.")
    @Test
    void changeNumberOfGuestWithNegative() {
        OrderTable orderTable = new OrderTable("1번 테이블");
        orderTable.use();

        assertThatThrownBy(() -> orderTable.changeNumberOfGuest(-1))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("비어있는 주문 테이블의 손님 수를 변경할 수 없다.")
    @Test
    void changeNotOccupiedTableNumberOfGuest() {
        OrderTable orderTable = new OrderTable("1번 테이블");

        assertThatThrownBy(() -> orderTable.changeNumberOfGuest(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블을 사용한다.")
    @Test
    void use() {
        OrderTable orderTable = new OrderTable("1번 테이블");

        orderTable.use();

        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("주문 테이블을 사용한다.")
    @Test
    void useWithNumberOfGuest() {
        OrderTable orderTable = new OrderTable("1번 테이블");

        orderTable.use(10);

        assertAll(
                () -> assertThat(orderTable.isOccupied()).isTrue(),
                () -> assertThat(orderTable.getNumberOfGuest()).isEqualTo(10)
        );
    }

    @DisplayName("주문 테이블을 치운다.")
    @Test
    void clear() {
        OrderTableClearPolicy orderTableClearPolicy = new OrderTableClearPolicy(new InMemoryEatInOrderRepository());
        OrderTable orderTable = new OrderTable(1L, "1번 테이블");
        orderTable.use();

        orderTable.clear(orderTableClearPolicy);

        assertAll(
                () -> assertThat(orderTable.isOccupied()).isFalse(),
                () -> assertThat(orderTable.getNumberOfGuest()).isZero()
        );
    }
}
