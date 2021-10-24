package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.application.OrderTableObserver;
import kitchenpos.eatinorders.tobe.application.TobeOrderTableService;
import kitchenpos.eatinorders.tobe.domain.CompleteEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CompleteOrderTableObserver implements OrderTableObserver {
    private final TobeOrderTableService orderTableService;

    public CompleteOrderTableObserver(TobeOrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @EventListener
    @Override
    public void orderComplete(CompleteEvent event) {
        orderTableService.clear(event.getTableId());
    }
}
