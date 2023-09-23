package kitchenpos.order.domain;

import kitchenpos.order.eatinorders.domain.OrderTableRepository;
import kitchenpos.order.eatinorders.domain.exception.NotFoundOrderTableException;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;

@Service
public class EatInOrderCreateService {

    private final OrderLineItemsService orderLineItemsService;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderCreateService(OrderLineItemsService orderLineItemsService, OrderTableRepository orderTableRepository) {
        this.orderLineItemsService = orderLineItemsService;
        this.orderTableRepository = orderTableRepository;
    }

    public Order create(Order order) {
        OrderTable orderTable = getOrderTable(order);
        return OrderCreateFactory.eatInOrder(getOrderLineItems(order), orderTable);
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems());
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
