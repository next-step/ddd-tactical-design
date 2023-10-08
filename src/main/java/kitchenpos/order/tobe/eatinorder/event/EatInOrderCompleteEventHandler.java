package kitchenpos.order.tobe.eatinorder.event;

import java.util.NoSuchElementException;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus;
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EatInOrderCompleteEventHandler {

    private final OrderTableRepository orderTableRepository;
    private final EatInOrderRepository orderRepository;

    public EatInOrderCompleteEventHandler(OrderTableRepository orderTableRepository, EatInOrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
    }

    @Async
    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT,
        fallbackExecution = true
    )
    public void handleEatInOrderCompleteEvent(EatInOrderCompleteEvent event) {
        final var orderTable = orderTableRepository.findById(event.getOrderTableId())
            .orElseThrow(NoSuchElementException::new);
        orderTable.clear();
    }
}
