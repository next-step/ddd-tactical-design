package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kitchenpos.eatinorders.support.InMemoryEatInOrderRepository;

class EatInOrderTest {
    private OrderLineItems orderLineItems;
    private OrderTable orderTable;
    private EatInOrderCompletePolicy eatInOrderCompletePolicy = new EatInOrderCompletePolicy(new InMemoryEatInOrderRepository());

    @BeforeEach
    void init() {
        orderLineItems = new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 1000L)));
        orderTable = new OrderTable(UUID.randomUUID(), "1번 테이블");
        orderTable.use();
    }

    @Test
    void 매장주문_생성_시_주문테이블은_사용중이어야_한다() {
        OrderLineItems orderLineItems = new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 1000L)));
        OrderTable orderTable = new OrderTable(UUID.randomUUID(), "1번 테이블");
        orderTable.use();

        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);

        assertThat(eatInOrder.getId()).isNotNull();
        assertThat(eatInOrder.getOrderDateTime()).isNotNull();
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @Test
    void 매장주문_생성_시_주문테이블은_사용안함이면_예외가_발생한다() {
        OrderLineItems orderLineItems = new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 1000L)));
        OrderTable orderTable = new OrderTable(UUID.randomUUID(), "1번 테이블");
        orderTable.clean();

        assertThatThrownBy(() -> new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("매장주문 시 주문테이블은 사용중이어야 합니다. 현재 값: false");
    }

    @Test
    void 대기중인_매장주문을_접수할_수_있다() {
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);

        eatInOrder.accept();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    void 대기중이_아닌_매장주문을_접수할_때_예외가_발생한다() {
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);
        eatInOrder.accept();

        assertThatThrownBy(() -> eatInOrder.accept())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("대기중인 매장주문만 접수할 수 있습니다. 현재 값: ACCEPTED");
    }

    @Test
    void 접수된_매장주문을_서빙할_수_있다() {
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);
        eatInOrder.accept();

        eatInOrder.serve();

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    void 접수_안된_매장주문_서빙할_때_예외가_발생한다() {
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);

        assertThatThrownBy(() -> eatInOrder.serve())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("접수된 매장주문만 서빙할 수 있습니다. 현재 값: WAITING");
    }

    @Test
    void 서빙된_매장주문을_완료할_수_있다() {
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);
        eatInOrder.accept();
        eatInOrder.serve();

        eatInOrder.complete(eatInOrderCompletePolicy);

        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    void 서빙_안된_매장주문_완료할_때_예외가_발생한다() {
        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);

        assertThatThrownBy(() -> eatInOrder.complete(eatInOrderCompletePolicy))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("서빙된 매장주문만 완료할 수 있습니다. 현재 값: WAITING");
    }
}
