package kitchenpos.domain.order.tobe.domain;

import kitchenpos.domain.support.Guest;
import kitchenpos.domain.support.Name;
import kitchenpos.domain.support.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class EatInOrderTest {

    @DisplayName("매장 주문을 생성한다")
    @Test
    void constructor() {
        OrderTable orderTable = new OrderTable(new Name("테이블A"), new Guest(1));
        EatInOrder eatInOrder = new EatInOrder(orderTable, List.of(new OrderLineItem(UUID.randomUUID(), 1, new Price(BigDecimal.valueOf(1_000)))));

        assertThat(eatInOrder.orderTable()).isNotNull();
        assertThat(eatInOrder.orderLineItems()).hasSize(1);
        assertThat(eatInOrder.status()).isEqualTo(OrderStatus.WAITING);
    }

    @DisplayName("매장 주문 생성 시, 주문 항목이 비어있으면 생성을 실패한다.")
    @Test
    void constructor_fail() {
        OrderTable orderTable = new OrderTable(new Name("테이블A"), new Guest(1));
        assertThatIllegalArgumentException().isThrownBy(() -> new EatInOrder(orderTable, List.of()));
    }

    @DisplayName("매장 주문 상태가 Eat-In Order Waiting이면, Eat-In Order Accepted로 변경할 수 있다")
    @Test
    void accept() {
        // given
        OrderTable orderTable = new OrderTable(new Name("테이블A"), new Guest(1));
        EatInOrder eatInOrder = new EatInOrder(orderTable, List.of(new OrderLineItem(UUID.randomUUID(), 1, new Price(BigDecimal.valueOf(1_000)))));

        // when
        eatInOrder.accepted();

        // then
        assertThat(eatInOrder.status()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("매장 주문 상태가 Eat-In Order Accepted이면, Eat-In Order Served로 변경할 수 있다")
    @Test
    void serve() {
        // given
        OrderTable orderTable = new OrderTable(new Name("테이블A"), new Guest(1));
        EatInOrder eatInOrder = new EatInOrder(orderTable, List.of(new OrderLineItem(UUID.randomUUID(), 1, new Price(BigDecimal.valueOf(1_000)))));
        eatInOrder.accepted();

        // when
        eatInOrder.serve();

        // then
        assertThat(eatInOrder.status()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("매장 주문 상태가 Eat-In Order Served이면, Eat-In Order Completed로 변경할 수 있다")
    @Test
    void complete() {
        // given
        OrderTable orderTable = new OrderTable(new Name("테이블A"), new Guest(1));
        EatInOrder eatInOrder = new EatInOrder(orderTable, List.of(new OrderLineItem(UUID.randomUUID(), 1, new Price(BigDecimal.valueOf(1_000)))));
        eatInOrder.accepted();
        eatInOrder.serve();

        // when
        eatInOrder.complete();

        // then
        assertThat(eatInOrder.status()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(eatInOrder.orderTable().isOccupied()).isFalse();
    }
}
