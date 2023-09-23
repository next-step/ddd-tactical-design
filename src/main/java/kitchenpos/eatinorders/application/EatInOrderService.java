package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.request.OrderCreateRequest;
import kitchenpos.eatinorders.application.dto.request.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.application.dto.response.OrderResponse;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.domain.OrderType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderService(
        final OrderRepository orderRepository,
        final OrderValidator orderValidator,
        final OrderTableRepository orderTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderResponse create(final OrderCreateRequest request) {
        final OrderType type = request.getType();
        final List<OrderLineItemCreateRequest> orderLineItemRequests = request.getOrderLineItemCreateRequests();

        orderValidator.validateOrderLineItemRequests(orderLineItemRequests);
        orderValidator.validateExistMenu(orderLineItemRequests);

        final List<OrderLineItem> orderLineItems = new ArrayList<>();
        for (final OrderLineItemCreateRequest orderLineItemRequest : orderLineItemRequests) {
            orderValidator.validateMenu(orderLineItemRequest);
            final OrderLineItem orderLineItem = new OrderLineItem(
                orderLineItemRequest.getMenuId(),
                orderLineItemRequest.getPrice(),
                orderLineItemRequest.getQuantity()
            );
            orderLineItems.add(orderLineItem);
        }

        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
            .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }

        Order order = new Order(type, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTable);
        return OrderResponse.of(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse accept(final UUID orderId) {
        final Order order = findOrder(orderId);
        order.accept();
        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse serve(final UUID orderId) {
        final Order order = findOrder(orderId);
        order.served();
        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse complete(final UUID orderId) {
        final Order order = findOrder(orderId);
        order.completed();
        final OrderTable orderTable = order.getOrderTable();
        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.clear();
        }
        return OrderResponse.of(order);
    }

    private Order findOrder(UUID orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
            .map(OrderResponse::of)
            .collect(Collectors.toList());
    }
}
