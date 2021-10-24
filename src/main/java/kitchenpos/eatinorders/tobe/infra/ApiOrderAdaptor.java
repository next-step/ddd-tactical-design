package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.application.TobeOrderService;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import org.springframework.stereotype.Component;

@Component
public class ApiOrderAdaptor implements OrderAdaptor {
    private final TobeOrderService orderService;

    public ApiOrderAdaptor(TobeOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean isOrderComplete(TobeOrderTable orderTable) {
        return orderService.isOrderComplete(orderTable.getId());
    }
}
