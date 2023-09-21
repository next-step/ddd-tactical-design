package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.domain.OrderFixture.acceptedOrder;
import static kitchenpos.eatinorders.tobe.domain.OrderFixture.completedOrder;
import static kitchenpos.eatinorders.tobe.domain.OrderFixture.order;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.eatinorders.tobe.domain.OrderTable.OrderTableStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTableTest {

    @DisplayName("주문테이블을 생성할수 있다")
    @Test
    void test1() {
        //given
        OrderTableName orderTableName = new OrderTableName("테이블이름");

        //when
        OrderTable orderTable = new OrderTable(orderTableName);

        //then
        assertAll(
            () -> assertThat(orderTable.getId()).isNotNull(),
            () -> assertThat(orderTable.getName()).isEqualTo(new OrderTableName("테이블이름")),
            () -> assertThat(orderTable.getStatus()).isEqualTo(OrderTableStatus.VACANT),
            () -> assertThat(orderTable.getNumberOfGuest()).isEqualTo(new NumberOfGuest(0))
        );
    }

    @DisplayName("주문테이블엔 이름이 있어야 한다")
    @Test
    void test2() {
        //when && then
        assertThatThrownBy(
            () -> new OrderTable(null)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문테이블은 이름을 가지고 있어야 합니다");
    }

    @DisplayName("주문테이블의 상태를 확인할수 있다")
    @Test
    void test3() {
        //when
        OrderTable orderTable = new OrderTable(new OrderTableName("name"));
        //then
        assertAll(
            () -> assertThat(orderTable.isOccupied()).isFalse(),
            () -> assertThat(orderTable.isVacant()).isTrue()
        );
    }

    @DisplayName("주문테이블의 상태를 변경할수 있다")
    @Test
    void test4() {
        //given
        OrderTable orderTable = new OrderTable(new OrderTableName("name"));

        //when
        orderTable.occupy();

        //then
        assertAll(
            () -> assertThat(orderTable.isOccupied()).isTrue(),
            () -> assertThat(orderTable.isVacant()).isFalse()
        );
    }

    @DisplayName("주문이 완료됬을때, 모든 주문이 완료된 상태라면 테이블을 정리한다")
    @Test
    void test5() {
        //given
        Orders allCompleteOrder = new Orders(List.of(completedOrder(), completedOrder()));
        OrderTable orderTable = OrderFixture.occupiedOrderTable(allCompleteOrder);

        Order order = order(orderTable);

        //when
        order.accept();
        order.serve();
        order.complete();

        //then
        assertAll(
            () -> assertThat(orderTable.isVacant()).isTrue(),
            () -> assertThat(orderTable.getNumberOfGuest()).isEqualTo(NumberOfGuest.INIT)
        );

    }

    @DisplayName("주문이 완료됬을때, 모든 주문이 완료된 상태가 아니라면 테이블을 정리하지 않는다")
    @Test
    void test6() {
        //given
        Orders notCompleteOrder = new Orders(List.of(completedOrder(), acceptedOrder()));
        OrderTable orderTable = OrderFixture.occupiedOrderTable(notCompleteOrder);

        Order order = order(orderTable);

        //when
        order.accept();
        order.serve();
        order.complete();

        //then
        assertThat(orderTable.isOccupied()).isTrue();
    }
}
