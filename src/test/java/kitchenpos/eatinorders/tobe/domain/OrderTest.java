package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.domain.OrderFixture.acceptedOrder;
import static kitchenpos.eatinorders.tobe.domain.OrderFixture.completedOrder;
import static kitchenpos.eatinorders.tobe.domain.OrderFixture.order;
import static kitchenpos.eatinorders.tobe.domain.OrderFixture.servedOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.eatinorders.tobe.domain.Order.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @DisplayName("주문을 생성할수 있다")
    @Test
    void test1() {
        Order order = OrderFixture.order();

        //then
        assertAll(
            () -> assertThat(order.getId()).isNotNull(),
            () -> assertThat(order.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(order.getOrderDateTime()).isNotNull(),
            () -> assertThat(order.getOrderLineItems())
                .extracting("order")
                .containsExactlyInAnyOrder(order, order)

        );
    }

    @DisplayName("사용중인 주문테이블에만 주문을 등록할수 있다")
    @Test
    void test2() {
        //given
        OrderTable vacantTable = new OrderTable(new OrderTableName("name"));
        //when && then
        assertThatThrownBy(
            () -> order(vacantTable)
        ).isInstanceOf(IllegalStateException.class)
            .hasMessage("주문테이블이 이용중이 아닙니다");
    }

    @DisplayName("주문내역이 있어야 주문을 등록할수 있다")
    @Test
    void test3() {
        //given
        OrderLineItems orderLineItems = new OrderLineItems(List.of());

        //when && then
        assertThatThrownBy(
            () -> order(orderLineItems)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문내역이 하나 이상 있어야 합니다");
    }

    @DisplayName("대기중인 주문만 수락할수 있다")
    @Test
    void test4() {
        assertAll(
            () -> assertThatThrownBy(
                () -> acceptedOrder().accept()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("접수 대기 중인 주문만 수락할수 있습니다"),
            () -> assertThatThrownBy(
                () -> servedOrder().accept()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("접수 대기 중인 주문만 수락할수 있습니다"),
            () -> assertThatThrownBy(
                () -> completedOrder().accept()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("접수 대기 중인 주문만 수락할수 있습니다")
        );
    }

    @DisplayName("수락된 주문만 전달할수 있다")
    @Test
    void test5() {
        assertAll(
            () -> assertThatThrownBy(
                () -> servedOrder().serve()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("수락된 주문만 전달할수 있습니다"),
            () -> assertThatThrownBy(
                () -> completedOrder().serve()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("수락된 주문만 전달할수 있습니다")
        );
    }

    @DisplayName("전달된 주문만 완료할수 있다")
    @Test
    void test6() {
        assertAll(
            () -> assertThatThrownBy(
                () -> acceptedOrder().complete()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("전달된 주문만 완료할수 있습니다"),
            () -> assertThatThrownBy(
                () -> completedOrder().complete()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("전달된 주문만 완료할수 있습니다")
        );
    }
}
