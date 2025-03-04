package kitchenpos.eatinorders.presentation;

import kitchenpos.eatinorders.tobe.domain.ClearOrderTableService;
import kitchenpos.eatinorders.tobe.domain.OrderCompleteEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderCompleteEventHandler {
    private final ClearOrderTableService clearOrderTableService;

    public OrderCompleteEventHandler(ClearOrderTableService clearOrderTableService) {
        this.clearOrderTableService = clearOrderTableService;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handle(OrderCompleteEvent event) {
        clearOrderTableService.clearOrderTable(event.getId());
    }
}
