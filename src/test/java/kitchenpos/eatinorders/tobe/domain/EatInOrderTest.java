package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.NewFixtures;
import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("매장주문 테스트")
class EatInOrderTest {

    private EatInOrderTable eatInOrderTable;
    private EatInOrderLineItems eatInOrderLineItems;

    @BeforeEach
    void setUp() {
        eatInOrderLineItems = EatInOrderLineItems.create(List.of(NewFixtures.eatInOrderLineItem()));
        eatInOrderTable = NewFixtures.orderTable(true, 4);
    }

    @DisplayName("매장주문 생성 성공")
    @Test
    void create() {
        UUID id = UUID.randomUUID();

        EatInOrder eatInOrder = EatInOrder.create(id, OrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);

        assertThat(eatInOrder).isEqualTo(EatInOrder.create(id, OrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable));
    }

    @DisplayName("테이블이 비어있으면 매장주문을 생성할 수 없다.")
    @Test
    void create_failed() {
        EatInOrderTable emptyTable = NewFixtures.orderTable(false, 0);

        assertThatThrownBy( () -> EatInOrder.create(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems, emptyTable))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(NOT_OCCUPIED_ORDER_TABLE);
    }

    @DisplayName("주문상태가 접수 대기가 아니면 주문을 수락할 수 없다.")
    @Test
    void accept_failed() {
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), OrderStatus.COMPLETED, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);

        assertThatThrownBy( () -> eatInOrder.accept())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ORDER_STATUS_NOT_WAITING);
    }

    @DisplayName("주문상태가 접수대기면 주문을 접수할 수 있다.")
    @Test
    void accept() {
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);

        eatInOrder.accept();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("주문상태가 접수가 아니면 주문을 서빙할 수 없다.")
    @Test
    void serve_failed() {
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);

        assertThatThrownBy( () -> eatInOrder.serve())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ORDER_STATUS_NOT_ACCEPTED);
    }

    @DisplayName("주문상태가 접수면 주문을 서빙할 수 있다.")
    @Test
    void serve() {
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), OrderStatus.ACCEPTED, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);

        eatInOrder.serve();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("주문상태가 서빙이 아니면 주문을 완료할 수 없다.")
    @Test
    void complete_failed() {
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);

        assertThatThrownBy( () -> eatInOrder.complete())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ORDER_STATUS_NOT_SERVED);
    }

    @DisplayName("주문상태가 서빙이면 주문을 완료할 수 있다.")
    @Test
    void complete() {
        EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), OrderStatus.SERVED, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTable);

        eatInOrder.complete();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}