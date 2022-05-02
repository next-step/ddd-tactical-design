package kitchenpos.eatinorders.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableTest {

    @DisplayName("주문 테이블 생성.")
    @Test
    void create() {
        OrderTableName name = new OrderTableName("테이블 명");
        assertDoesNotThrow(() -> new OrderTable(name));
    }

    @DisplayName("주문 테이블을 생성한다. 초기 생성시 손님 숫자는 0명, 공석으로 생성된다.")
    @Test
    void init_value() {
        OrderTable orderTable = orderTable();

        assertAll(
                () -> assertThat(orderTable.isEmpty()).isTrue(),
                () -> assertThat(orderTable.getNumberOfGuests().getNumberOfGuests()).isZero()
        );
    }

    @DisplayName("주문 테이블의 empty 값이 true 일때 착석 요청시 예외.")
    @Test
    void sit_exception() {
        OrderTable sut = orderTable();
        sut.sit();

        assertThatThrownBy(() -> sut.sit()).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블에 착성 요청시 empty 값이 false 로 변경 된다.")
    @Test
    void sit() {
        OrderTable sut = orderTable();
        sut.sit();

        assertThat(sut.isEmpty()).isFalse();
    }

    private OrderTable orderTable() {
        OrderTableName name = new OrderTableName("테이블 명");
        return new OrderTable(name);
    }
}
