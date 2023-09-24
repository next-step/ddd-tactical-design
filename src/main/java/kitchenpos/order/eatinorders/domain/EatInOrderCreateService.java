package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItemsValidService;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.eatinorders.domain.exception.NotFoundOrderTableException;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;

@Service
public class EatInOrderCreateService {

    private final OrderLineItemsValidService orderLineItemsValidService;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderCreateService(OrderTableRepository orderTableRepository, OrderLineItemsValidService orderLineItemsValidService) {
        this.orderLineItemsValidService = orderLineItemsValidService;
        this.orderTableRepository = orderTableRepository;
    }

    public Order create(Order order) {
        OrderTable orderTable = getOrderTable(order);
        orderLineItemsValidService.valid(order.getOrderLineItems());
        return OrderCreateFactory.eatInOrder(order, orderTable);
    }


    private OrderTable getOrderTable(Order order) {
        final OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(NotFoundOrderTableException::new);

        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        return orderTable;
    }
}
