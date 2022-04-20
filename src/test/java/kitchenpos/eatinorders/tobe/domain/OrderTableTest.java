package kitchenpos.eatinorders.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderTableTest {
    private OrderTable orderTable;

    @Test
    void assign() {
        //given
        orderTable = new OrderTable("1번 테이블");

        //when
        OrderTable assignedOrderTable = orderTable.assign(4);

        //then
        Assertions.assertThat(assignedOrderTable.isEmpty()).isFalse();
        Assertions.assertThat(assignedOrderTable.getNumberOfGuests()).isEqualTo(4);
    }

    @Test
    void assign_when_order_table_is_not_empty() {
        //given
        orderTable = new OrderTable("1번 테이블");
        OrderTable assignedOrderTable = orderTable.assign(4);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> assignedOrderTable.assign(3)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void clear() {
        //given
        orderTable = new OrderTable("1번 테이블");
        OrderTable assignedOrderTable = orderTable.assign(4);

        //when
        OrderTable emptyOrderTable = assignedOrderTable.clear();

        //then
        Assertions.assertThat(emptyOrderTable.isEmpty()).isTrue();
    }

    @Test
    void changeNumberOfGuests() {
        //given
        orderTable = new OrderTable("1번 테이블");
        OrderTable assignedOrderTable = orderTable.assign(4);

        //when
        OrderTable changedOrderTable = assignedOrderTable.changeNumberOfGuests(3);

        //then
        Assertions.assertThat(changedOrderTable.getNumberOfGuests()).isEqualTo(3);
    }

    @Test
    void changeNumberOfGuests_when_order_table_is_empty() {
        //given
        orderTable = new OrderTable("1번 테이블");

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> orderTable.changeNumberOfGuests(3)
        ).isInstanceOf(IllegalStateException.class);
    }
}
