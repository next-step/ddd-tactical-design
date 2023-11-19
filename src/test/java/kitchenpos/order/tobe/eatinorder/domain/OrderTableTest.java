package kitchenpos.order.tobe.eatinorder.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.Fixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderTableTest {

    @DisplayName("주문 테이블을 초기화한다.")
    @Test
    void testChangeEmptyTable() {
        // given
        OrderTable orderTable = Fixtures.orderTable(true, 3);

        // when
        orderTable.changeEmptyTable();

        // then
        assertThat(orderTable.getNumberOfGuests()).isZero();
        assertThat(orderTable.isOccupied()).isFalse();
    }

    @DisplayName("주문 테이블을 사용중인 테이블로 변경한다.")
    @Test
    void testChangeInUseTable() {
        // given
        OrderTable orderTable = Fixtures.orderTable(false, 0);

        // when
        orderTable.changeInUseTable();

        // then
        assertThat(orderTable.getNumberOfGuests()).isZero();
        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("주문 테이블의 손님 수를 변경한다.")
    @Test
    void testChangeNumberOfGuests() {
        // given
        OrderTable orderTable = Fixtures.orderTable(true, 2);
        int expectedNumberOfGuests = 3;

        // when
        orderTable.changeNumberOfGuests(expectedNumberOfGuests);

        // then
        assertThat(orderTable.getNumberOfGuests()).isEqualTo(expectedNumberOfGuests);
        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("빈 주문 테이블은 손님 수를 변경할 수 없다")
    @Test
    void testChangeNumberOfGuestsIfEmptyOrderTable() {
        // given
        OrderTable orderTable = Fixtures.emptyOrderTable();
        int expectedNumberOfGuests = 3;

        // when // then
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(expectedNumberOfGuests))
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블이 비어있는지 확인한다")
    @ParameterizedTest
    @CsvSource(value = {"false;true", "true;false"}, delimiter = ';')
    void testIsEmpty(boolean occupied, boolean expected) {
        // given
        var orderTable = Fixtures.orderTable(occupied, 0);

        // when
        boolean actual = orderTable.isEmpty();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
