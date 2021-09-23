package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.dto.CreateOrderRequest;
import kitchenpos.eatinorders.tobe.dto.OrderCompletedResponse;
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
    private final OrderTableTranslator orderTableTranslator;
    private final OrderDomainService orderDomainService;

    public OrderService(
            final OrderRepository orderRepository,
            final OrderTableTranslator orderTableTranslator,
            final OrderDomainService orderDomainService
    ) {
        this.orderRepository = orderRepository;
        this.orderTableTranslator = orderTableTranslator;
        this.orderDomainService = orderDomainService;
    }

    @Transactional
    public OrderResponse create(final CreateOrderRequest request) {
        request.validate();
        final OrderLineItems orderLineItems = new OrderLineItems(request.getOrderLineItems().stream()
                .map(item -> new OrderLineItem(item.getMenuId(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList()));
        final Order order = new Order(
                request.getType(),
                orderLineItems,
                request.getDeliveryAddress(),
                request.getOrderTableId(),
                orderTableTranslator
        );
        orderDomainService.validateOrder(order);
        orderRepository.save(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse accept(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept(orderDomainService);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse serve(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.serve();
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse startDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.startDelivery();
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse completeDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.completeDelivery();
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse complete(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.complete(orderTableTranslator);
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
                orderDomainService.getOrderLineItems(order),
                order.getDeliveryAddress(),
                orderTableTranslator.getOrderTable(order.getOrderTableId()),
                order.getOrderTableId()
        );
    }
}
