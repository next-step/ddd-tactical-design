package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableManager;
import org.springframework.stereotype.Component;

@Component
public class OrderDomainService {
    private final OrderTableManager orderTableManager;

    public OrderDomainService(final OrderTableManager orderTableManager) {
        this.orderTableManager = orderTableManager;
    }

    public OrderTable getOrderTable(final Order order) {
        return orderTableManager.getOrderTable(order.getOrderTableId());
    }

}
