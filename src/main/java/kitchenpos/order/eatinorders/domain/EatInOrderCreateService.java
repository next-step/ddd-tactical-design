package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.*;
import kitchenpos.order.eatinorders.domain.exception.NotFoundOrderTableException;
import kitchenpos.order.eatinorders.domain.vo.OrderLineItems;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

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
        Order eatInOrder = new Order(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), getOrderLineItems(order), null, orderTable, orderTable.getId());
        return orderRepository.save(eatInOrder);
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
