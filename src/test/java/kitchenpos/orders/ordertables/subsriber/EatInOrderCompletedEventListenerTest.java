package kitchenpos.orders.ordertables.subsriber;

import kitchenpos.orders.eatinorders.application.EatInOrderService;
import kitchenpos.orders.eatinorders.domain.EatInOrder;
import kitchenpos.orders.eatinorders.domain.EatInOrderRepository;
import kitchenpos.orders.eatinorders.domain.EatInOrderStatus;
import kitchenpos.orders.ordertables.fixture.OrderTableFixture;
import kitchenpos.common.events.EatInOrderCompletedEvent;
import kitchenpos.orders.ordertables.application.OrderTableService;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.domain.OrderTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static kitchenpos.orders.eatinorders.fixture.EatInOrderFixture.order;
import static kitchenpos.orders.ordertables.fixture.OrderTableFixture.orderTable;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RecordApplicationEvents
@DisplayName("테이블 치우기 이벤트 발행")
class EatInOrderCompletedEventListenerTest {

    @Autowired
    OrderTableRepository orderTableRepository;

    @Autowired
    EatInOrderRepository eatInOrderRepository;

    @Autowired
    EatInOrderService eatInOrderService;

    @Autowired
    OrderTableService orderTableService;

    @Autowired
    ApplicationEvents events;

    @DisplayName("주문이 완료되면, 주문 테이블 청소 이벤트가 발행된다.")
    @Test
    void completeEatInOrder() {
        assertThat(events.stream(EatInOrderCompletedEvent.class)).hasSize(0);
        final OrderTable orderTable = orderTableRepository.save(OrderTableFixture.orderTable(true, 4));
        final EatInOrder expected = eatInOrderRepository.save(order(EatInOrderStatus.SERVED, orderTable));
        expected.complete();
        eatInOrderRepository.save(expected);
        assertThat(events.stream(EatInOrderCompletedEvent.class)).hasSize(1);
    }

}
