package kitchenpos.eatinorder.adapter.in.eventlistener;

import kitchenpos.eatinorder.application.port.in.ClearOrderTableUseCase;
import kitchenpos.eatinorder.domain.event.EatInOrderCompletedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EatInOrderCompletedEventListener {
    private final ClearOrderTableUseCase clearOrderTableUseCase;

    public EatInOrderCompletedEventListener(
            final ClearOrderTableUseCase clearOrderTableUseCase
    ) {
        this.clearOrderTableUseCase = clearOrderTableUseCase;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handleProductPriceChange(EatInOrderCompletedEvent event) {
        clearOrderTableUseCase.clear(event.orderTableId());
    }
}

