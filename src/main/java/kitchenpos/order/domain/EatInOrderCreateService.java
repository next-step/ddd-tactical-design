package kitchenpos.order.domain;

import kitchenpos.order.eatinorders.domain.OrderTableRepository;
import kitchenpos.order.eatinorders.domain.exception.NotFoundOrderTableException;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;

@Service
public class EatInOrderCreateService {

    private final OrderLineItemsValidService orderLineItemsValidService;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderCreateService(OrderLineItemsValidService orderLineItemsValidService, OrderTableRepository orderTableRepository) {
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
