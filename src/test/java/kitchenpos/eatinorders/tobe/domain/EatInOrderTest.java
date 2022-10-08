package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderTest {
    private List<OrderLineItem> orderLineItems = List.of(
            new OrderLineItem(1L, BigDecimal.valueOf(20000), 2),
            new OrderLineItem(2L, BigDecimal.valueOf(22000), 2)
    );

    @DisplayName("매장 주문을 생성한다.")
    @Test
    void create() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L);

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @DisplayName("매장 주문을 접수한다.")
    @Test
    void accept() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L);

        eatInOrder.accept();

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("WAITING 상태가 아닌 매장 주문은 접수할 수 없다.")
    @Test
    void acceptFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L);
        eatInOrder.accept();

        assertThatThrownBy(eatInOrder::accept)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문을 서빙한다.")
    @Test
    void serve() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L);
        eatInOrder.accept();

        eatInOrder.serve();

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("ACCEPT 상태가 아닌 매장 주문은 서빙할 수 없다.")
    @Test
    void serveFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L);

        assertThatThrownBy(eatInOrder::serve)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문을 완료한다.")
    @Test
    void complete() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L);
        eatInOrder.accept();
        eatInOrder.serve();

        eatInOrder.complete();

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("SERVED 상태가 아닌 매장 주문은 완료할 수 없다.")
    @Test
    void completeFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L);

        assertThatThrownBy(eatInOrder::complete)
                .isInstanceOf(IllegalStateException.class);
    }
}
