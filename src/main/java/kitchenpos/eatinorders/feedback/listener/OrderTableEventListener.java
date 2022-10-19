package kitchenpos.eatinorders.feedback.listener;

import kitchenpos.eatinorders.feedback.domain.OrderTable;
import kitchenpos.eatinorders.feedback.domain.OrderTableRepository;
import kitchenpos.eatinorders.feedback.domain.OrderTableClearPolicyImpl;
import kitchenpos.eatinorders.feedback.domain.OrderCompletedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderTableEventListener {
    private final OrderTableClearPolicyImpl orderTableClearPolicy;
    private final OrderTableRepository orderTableRepository;

    public OrderTableEventListener(OrderTableClearPolicyImpl orderTableClearPolicy, OrderTableRepository orderTableRepository) {
        this.orderTableClearPolicy = orderTableClearPolicy;
        this.orderTableRepository = orderTableRepository;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handle(OrderCompletedEvent event) {
        OrderTable orderTable = findById(event.getOrderTableId());
        orderTable.clear(orderTableClearPolicy);
    }

    private OrderTable findById(Long orderTableId) {
        return orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
    }
}
