package kitchenpos.ordertables.subsriber;

import kitchenpos.eatinorders.publisher.OrderTableClearEvent;
import kitchenpos.ordertables.application.OrderTableService;
import kitchenpos.ordertables.domain.OrderTableId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderTableClearEventListener {

    private final OrderTableService orderTableService;

    public OrderTableClearEventListener(OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @TransactionalEventListener
    public void listen(OrderTableClearEvent event) {
        OrderTableId orderTableId = new OrderTableId(event.getOrderTableId());
        orderTableService.clear(orderTableId);
    }

}
