package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.Order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {

    private OrderTable orderTable;
    private Order waitingOrder;
    private Order acceptedOrder;
    private Order servedOrder;
    private Order completedOrder;

    @BeforeEach
    public void init() {
        this.orderTable = new OrderTable(new OrderTableName("name"));
        waitingOrder = createOrder();

        acceptedOrder = createOrder();
        acceptedOrder.accept();

        servedOrder = createOrder();
        servedOrder.accept();
        servedOrder.serve();

        completedOrder = createOrder();
        completedOrder.accept();
        completedOrder.serve();
        completedOrder.complete();
    }

    private Order createOrder() {
        OrderLineItem orderLineItem1 = new OrderLineItem(UUID.randomUUID(), new OrderLineItemQuantity(1));
        OrderLineItems orderLineItems = new OrderLineItems(List.of(orderLineItem1));
        OrderTable orderTable = new OrderTable(new OrderTableName("name"));
        orderTable.occupy();

        return new Order(orderTable, orderLineItems);
    }

    @DisplayName("주문을 생성할수 있다")
    @Test
    void test1() {
        //given
        orderTable.occupy();
        OrderLineItem orderLineItem1 = new OrderLineItem(UUID.randomUUID(), new OrderLineItemQuantity(1));
        OrderLineItem orderLineItem2 = new OrderLineItem(UUID.randomUUID(), new OrderLineItemQuantity(1));
        OrderLineItems orderLineItems = new OrderLineItems(List.of(orderLineItem1, orderLineItem2));

        //when
        Order order = new Order(orderTable, orderLineItems);

        //then
        assertAll(
            () -> assertThat(order.getId()).isNotNull(),
            () -> assertThat(order.getStatus()).isEqualTo(OrderStatus.WAITING),
            () -> assertThat(order.getOrderDateTime()).isNotNull(),
            () -> assertThat(order.getOrderTable()).isEqualTo(orderTable),
            () -> assertThat(order.getOrderLineItems())
                .extracting("order")
                .containsExactlyInAnyOrder(order, order)

        );
    }

    @DisplayName("사용중인 주문테이블에만 주문을 등록할수 있다")
    @Test
    void test2() {
        //given
        OrderLineItem orderLineItem1 = new OrderLineItem(UUID.randomUUID(), new OrderLineItemQuantity(1));
        OrderLineItem orderLineItem2 = new OrderLineItem(UUID.randomUUID(), new OrderLineItemQuantity(1));
        OrderLineItems orderLineItems = new OrderLineItems(List.of(orderLineItem1, orderLineItem2));

        //when && then
        assertThatThrownBy(
            () -> new Order(orderTable, orderLineItems)
        ).isInstanceOf(IllegalStateException.class)
            .hasMessage("주문테이블이 이용중이 아닙니다");
    }

    @DisplayName("주문내역이 있어야 주문을 등록할수 있다")
    @Test
    void test3() {
        //given
        orderTable.occupy();
        OrderLineItems orderLineItems = new OrderLineItems(List.of());

        //when && then
        assertThatThrownBy(
            () -> new Order(orderTable, orderLineItems)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문내역이 하나 이상 있어야 합니다");
    }

    @DisplayName("대기중인 주문만 수락할수 있다")
    @Test
    void test4() {
        assertAll(
            () -> assertThatThrownBy(
                () -> acceptedOrder.accept()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("접수 대기 중인 주문만 수락할수 있습니다"),
            () -> assertThatThrownBy(
                () -> servedOrder.accept()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("접수 대기 중인 주문만 수락할수 있습니다"),
            () -> assertThatThrownBy(
                () -> completedOrder.accept()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("접수 대기 중인 주문만 수락할수 있습니다")
        );
    }

    @DisplayName("수락된 주문만 전달할수 있다")
    @Test
    void test5() {
        assertAll(
            () -> assertThatThrownBy(
                () -> waitingOrder.serve()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("수락된 주문만 전달할수 있습니다"),
            () -> assertThatThrownBy(
                () -> servedOrder.serve()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("수락된 주문만 전달할수 있습니다"),
            () -> assertThatThrownBy(
                () -> completedOrder.serve()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("수락된 주문만 전달할수 있습니다")
        );
    }

    @DisplayName("전달된 주문만 완료할수 있다")
    @Test
    void test6() {
        assertAll(
            () -> assertThatThrownBy(
                () -> waitingOrder.complete()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("전달된 주문만 완료할수 있습니다"),
            () -> assertThatThrownBy(
                () -> acceptedOrder.complete()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("전달된 주문만 완료할수 있습니다"),
            () -> assertThatThrownBy(
                () -> completedOrder.complete()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessage("전달된 주문만 완료할수 있습니다")
        );
    }
}
