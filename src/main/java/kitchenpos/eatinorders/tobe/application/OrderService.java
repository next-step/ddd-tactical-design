package kitchenpos.eatinorders.tobe.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.dto.CreateOrderRequest;
import kitchenpos.eatinorders.tobe.dto.OrderCompletedResponse;
import kitchenpos.eatinorders.tobe.dto.OrderLineItemResponse;
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
    private final MenuTranslator menuTranslator;
    private final OrderTableTranslator orderTableTranslator;
    private final KitchenridersClient kitchenridersClient;

    public OrderService(
            final OrderRepository orderRepository,
            final MenuTranslator menuTranslator,
            final OrderTableTranslator orderTableTranslator,
            final KitchenridersClient kitchenridersClient
    ) {
        this.orderRepository = orderRepository;
        this.menuTranslator = menuTranslator;
        this.orderTableTranslator = orderTableTranslator;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public OrderResponse create(final CreateOrderRequest request) {
        request.validate();
        final List<OrderLineItem> orderLineItems = request.getOrderLineItems().stream()
                .map(item -> new OrderLineItem(item.getMenuId(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());
        final Order order = new Order(
                request.getType(),
                new OrderLineItems(orderLineItems, request.getType(), menuTranslator),
                request.getDeliveryAddress(),
                request.getOrderTableId(),
                orderTableTranslator
        );
        orderRepository.save(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse accept(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept(menuTranslator, kitchenridersClient);
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
        final List<OrderLineItemResponse> orderLineItems = order.getOrderLineItems().stream()
                .map(item -> new OrderLineItemResponse(
                        item.getSeq(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getMenuId(),
                        menuTranslator.getMenu(item))
                ).collect(Collectors.toList());
        return new OrderResponse(
                order.getId(),
                order.getType(),
                order.getStatus(),
                order.getOrderDateTime(),
                orderLineItems,
                order.getDeliveryAddress(),
                orderTableTranslator.getOrderTable(order.getOrderTableId()),
                order.getOrderTableId()
        );
    }
}
