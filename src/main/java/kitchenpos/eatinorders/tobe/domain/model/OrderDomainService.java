package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableManager;
import org.springframework.stereotype.Component;

@Component
public class OrderDomainService {

    private final OrderTableManager orderTableManager;
    private final MenuDomainService menuDomainService;

    public OrderDomainService(final OrderTableManager orderTableManager, final MenuDomainService menuDomainService) {
        this.orderTableManager = orderTableManager;
        this.menuDomainService = menuDomainService;
    }

    public OrderTable getOrderTable(final Order order) {
        return orderTableManager.getOrderTable(order.getOrderTableId());
    }

    public void validateOrder(final Order order) {
        if (order.getType() == OrderType.EAT_IN) {
            final OrderTable orderTable = orderTableManager.getOrderTable(order.getOrderTableId());
            if (orderTable.isEmpty()) {
                throw new IllegalStateException("비어있는 테이블에는 주문할 수 없습니다.");
            }
        }
        menuDomainService.validateOrder(order);
    }
}
