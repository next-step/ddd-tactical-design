package kitchenpos.eatinorders.ordertable.application.handler;

import kitchenpos.common.event.LastEatInOrderCompletedEvent;
import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTableCleanUp;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderTableEventHandler {

    private final OrderTableCleanUp orderTableCleanUp;

    public OrderTableEventHandler(OrderTableCleanUp orderTableCleanUp) {
        this.orderTableCleanUp = orderTableCleanUp;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void lastEatInOrderCompleted(final LastEatInOrderCompletedEvent event) {
        orderTableCleanUp.clear(event.getOrderTableId());
    }
}
