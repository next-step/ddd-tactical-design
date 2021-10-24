package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.application.TobeOrderService;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import org.springframework.stereotype.Component;

@Component
public class OrderHandlerAdaptor implements OrderAdaptor {
    private final TobeOrderService orderService;

    public OrderHandlerAdaptor(TobeOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean isOrderComplete(TobeOrderTable orderTable) {
        return orderService.isOrderComplete(orderTable.getId());
    }
}
