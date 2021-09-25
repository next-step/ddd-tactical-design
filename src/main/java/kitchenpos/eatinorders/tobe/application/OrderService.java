package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.dto.CreateOrderRequest;
import kitchenpos.eatinorders.tobe.dto.OrderCompletedResponse;
import kitchenpos.eatinorders.tobe.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.tobe.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("TobeOrderService")
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;

    public OrderService(final OrderRepository orderRepository, final OrderDomainService orderDomainService) {
        this.orderRepository = orderRepository;
        this.orderDomainService = orderDomainService;
    }

    @Transactional
    public OrderResponse create(final CreateOrderRequest request) {
        request.validate();
        final List<OrderLineItem> orderLineItems = request.getOrderLineItems().stream()
                .map(this::createOrderLineItem)
                .collect(Collectors.toList());
        final Order order = new Order(
                request.getType(),
                orderLineItems,
                request.getDeliveryAddress(),
                request.getOrderTableId()
        );
        orderDomainService.validateOrder(order);
        orderRepository.save(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse accept(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        orderDomainService.acceptOrder(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse serve(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        orderDomainService.serveOrder(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse startDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        orderDomainService.startDelivery(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse completeDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        orderDomainService.completeDelivery(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse complete(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        orderDomainService.completeOrder(order);
        return createOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public OrderCompletedResponse isCompleted(final UUID orderTableId) {
        return new OrderCompletedResponse(!orderRepository.existsByOrderTableIdAndNotCompleted(orderTableId));
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    private OrderResponse createOrderResponse(final Order order) {
        return new OrderResponse(
                order.getId(),
                order.getType(),
                order.getStatus(),
                order.getOrderDateTime(),
                order.getOrderLineItems(),
                order.getDeliveryAddress(),
                orderDomainService.getOrderTable(order),
                order.getOrderTableId()
        );
    }

    private OrderLineItem createOrderLineItem(final OrderLineItemRequest orderLineItemRequest) {
        return new OrderLineItem(orderLineItemRequest.getMenuId(),
                new OrderQuantity(orderLineItemRequest.getQuantity()),
                orderLineItemRequest.getPrice());
    }
}
