package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.domain.exception.NotFoundOrderTableException;
import kitchenpos.eatinorders.domain.vo.OrderLineItems;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class EatInOrderCreateService {

    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final EatInOrderLineItemsService eatInOrderLineItemsService;

    public EatInOrderCreateService(OrderRepository orderRepository, OrderTableRepository orderTableRepository, EatInOrderLineItemsService eatInOrderLineItemsService) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.eatInOrderLineItemsService = eatInOrderLineItemsService;
    }

    public Order create(Order order) {
        Order eatInOrder = new Order(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), getOrderLineItems(order), null, getOrderTable(order));
        return orderRepository.save(eatInOrder);
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return eatInOrderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems());
    }

    private OrderTable getOrderTable(Order order) {
        final OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(NotFoundOrderTableException::new);;
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        return orderTable;
    }
}
