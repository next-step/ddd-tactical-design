package kitchenpos.eatinorders.tobe.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.dto.CreateOrderRequest;
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
    public Order create(final CreateOrderRequest request) {
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
        return orderRepository.save(order);
    }

    @Transactional
    public Order accept(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept(menuTranslator, kitchenridersClient);
        return order;
    }

    @Transactional
    public Order serve(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.serve();
        return order;
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.startDelivery();
        return order;
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.completeDelivery();
        return order;
    }

    @Transactional
    public Order complete(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.complete(orderTableTranslator);
        return order;
    }

    @Transactional(readOnly = true)
    public boolean isCompleted(final UUID orderTableId) {
        return !orderRepository.existsByOrderTableIdAndNotCompleted(orderTableId);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
