package kitchenpos.eatinorders.feedback.infra;

import kitchenpos.eatinorders.feedback.domain.OrderTable;
import kitchenpos.eatinorders.feedback.domain.OrderTableRepository;
import kitchenpos.eatinorders.feedback.domain.OrderTableClearPolicy;
import kitchenpos.eatinorders.feedback.event.OrderCompleteEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrderTableEventListener {
    private final OrderTableClearPolicy orderTableClearPolicy;
    private final OrderTableRepository orderTableRepository;

    public OrderTableEventListener(OrderTableClearPolicy orderTableClearPolicy, OrderTableRepository orderTableRepository) {
        this.orderTableClearPolicy = orderTableClearPolicy;
        this.orderTableRepository = orderTableRepository;
    }

    @Async
    @EventListener
    public void handle(OrderCompleteEvent event) {
        OrderTable orderTable = findById(event.getOrderTableId());
        orderTable.clear(orderTableClearPolicy);
    }

    private OrderTable findById(Long orderTableId) {
        return orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
    }
}
