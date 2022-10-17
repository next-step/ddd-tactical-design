package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderTableTest {

    private OrderTable orderTable;

    @BeforeEach
    void setup() {
        orderTable = new OrderTable(new OrderTableName("기본 테이블"));
    }

    @Test
    @DisplayName("주문 테이블을 생성한다.")
    void createOrderTable() {
        // given
        OrderTableName name = new OrderTableName("주문 테이블");

        // when
        OrderTable createOrderTable = new OrderTable(name);

        // then
        assertThat(createOrderTable.nameValue()).isEqualTo("주문 테이블");
    }

    @Test
    @DisplayName("주문 테이블을 사용중으로 설정한다.")
    void setOrderTableOccupied() {
        // when
        orderTable.sit();

        // then
        assertThat(orderTable.occupied()).isEqualTo(true);
    }

    @Test
    @DisplayName("주문 테이블을 빈 테이블로 설정한다.")
    void clearOrderTable() {
        // when
        orderTable.clear();

        // then
        assertAll(
                () -> assertThat(orderTable.numberOfGuestsValue()).isEqualTo(0),
                () -> assertThat(orderTable.occupied()).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("주문 테이블의 손님 수를 변경한다.")
    void changeOrderTableNumberOfGuest() {
        // when
        orderTable.sit();
        orderTable.changeNumberOfGuest(5);

        // then
        assertThat(orderTable.numberOfGuestsValue()).isEqualTo(5);
    }

    @Test
    @DisplayName("주문 테이블의 손님 수를 변경한다 - 빈 테이블의 경우 IllegalStateException을 발생 시킨다.")
    void changeOrderTableNumberOfGuestIfEmptyTableThrowException() {
        // when
        // then
        assertThatThrownBy(() -> orderTable.changeNumberOfGuest(5))
                .isInstanceOf(IllegalStateException.class);
    }

}
