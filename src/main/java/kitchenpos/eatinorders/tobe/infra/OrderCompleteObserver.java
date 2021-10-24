package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.application.OrderTableObserver;
import kitchenpos.eatinorders.tobe.application.TobeOrderTableService;
import kitchenpos.eatinorders.tobe.domain.OrderCompleteEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderCompleteObserver implements OrderTableObserver {
    private final TobeOrderTableService orderTableService;

    public OrderCompleteObserver(TobeOrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @Override
    public void orderComplete(OrderCompleteEvent event) {
        orderTableService.clear(event.getTableId());
    }
}
