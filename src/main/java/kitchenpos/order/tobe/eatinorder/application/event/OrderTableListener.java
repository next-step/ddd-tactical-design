package kitchenpos.order.tobe.eatinorder.application.event;

import kitchenpos.common.event.publisher.OrderTableClearEvent;
import kitchenpos.order.application.OrderTableService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderTableListener {
    private final OrderTableService orderTableService;

    public OrderTableListener(OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @TransactionalEventListener
    public void handle(OrderTableClearEvent event) {
        orderTableService.clear(event.orderTableId());
    }
}
