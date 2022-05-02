package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.Order;
import kitchenpos.eatinorders.tobe.OrderLineItem;
import kitchenpos.eatinorders.tobe.OrderLineItems;
import kitchenpos.eatinorders.tobe.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTest {

    @DisplayName("매장 식사 주문을 생성한다. 초기 생성시 주문 상태는 '대기'로 설정 된다.")
    @Test
    void create_eat_in_order() {
        Order sut = eatInOrder();

        assertThat(sut.getStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @DisplayName("'대기' 상태가 아닌 매장 식사 주문에게 접수 요청을 할 경우 예외.")
    @Test
    void accept_exception() {
        Order sut = eatInOrder();
        sut.accept();

        assertThatThrownBy(() -> sut.accept()).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("'대기' 상태의 매장 식사 주문을 접수 할 경우 '접수' 상태로 변경 된다.")
    @Test
    void order_accept() {
        Order sut = eatInOrder();

        assertThat(sut.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName(" '접수' 상태가 아닌 매장 식사 주문을 서빙 요청을 할 경우 예외.")
    @Test
    void serve_exception() {
        Order sut = eatInOrder();

        assertThatThrownBy(() -> sut.serve()).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName(" '접수' 상태가 매장 식사 주문을 서빙 요청을 할 경우 예외 '서빙' 상태로 변경 된다.")
    @Test
    void serve() {
        Order sut = eatInOrder();
        sut.accept();
        sut.serve();

        assertThat(sut.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName(" '서빙' 상태가 아닌 매장 식사 주문에게 '완료' 요청을 하면 예외.")
    @Test
    void complete_exception() {
        Order sut = eatInOrder();
        sut.accept();

        assertThatThrownBy(() -> sut.complete()).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName(" '서빙' 상태의 매장 식사 주문에게 '완료' 요청을 하면 '완료' 상태로 변경 된다.")
    @Test
    void complete() {
        Order sut = eatInOrder();
        sut.accept();
        sut.serve();
        sut.complete();

        assertThat(sut.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    public Order eatInOrder() {
        OrderLineItems orderLineItems = new OrderLineItems(Arrays.asList(new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.TEN)));
        UUID orderTableId = UUID.randomUUID();

        return Order.createEatInOrder(orderLineItems, orderTableId);
    }
}
