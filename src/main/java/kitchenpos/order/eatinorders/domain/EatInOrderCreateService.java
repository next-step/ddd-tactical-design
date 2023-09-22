package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItemsService;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.eatinorders.domain.exception.NotFoundOrderTableException;
import kitchenpos.order.domain.vo.OrderLineItems;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderCreateService {

    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderLineItemsService orderLineItemsService;

    public EatInOrderCreateService(OrderRepository orderRepository, OrderTableRepository orderTableRepository, OrderLineItemsService orderLineItemsService) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderLineItemsService = orderLineItemsService;
    }

    public Order create(Order order) {
        OrderTable orderTable = getOrderTable(order);
        return orderRepository.save(OrderCreateFactory.eatInOrder(getOrderLineItems(order), orderTable));
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems(), order.getType());
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
