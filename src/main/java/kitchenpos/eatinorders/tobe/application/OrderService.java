package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableManager;
import kitchenpos.eatinorders.tobe.domain.service.OrderDeliverService;
import kitchenpos.eatinorders.tobe.domain.service.OrderValidateService;
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
    private final OrderValidateService orderValidateService;
    private final OrderDeliverService orderDeliverService;
    private final OrderTableManager orderTableManager;

    public OrderService(final OrderRepository orderRepository, final OrderValidateService orderValidateService, final OrderDeliverService orderDeliverService, final OrderTableManager orderTableManager) {
        this.orderRepository = orderRepository;
        this.orderValidateService = orderValidateService;
        this.orderDeliverService = orderDeliverService;
        this.orderTableManager = orderTableManager;
    }

    @Transactional
    public OrderResponse create(final CreateOrderRequest request) {
        request.validate();
        final OrderLineItems orderLineItems = new OrderLineItems(request.getOrderLineItems().stream()
                .map(this::createOrderLineItem)
                .collect(Collectors.toList()));
        final Order order = new Order(
                request.getType(),
                orderLineItems,
                request.getDeliveryAddress(),
                request.getOrderTableId()
        );
        orderValidateService.validateOrder(order);
        orderRepository.save(order);
        return createOrderResponse(order);
    }

    @Transactional
    public OrderResponse accept(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept(orderDeliverService);
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
        order.complete(orderTableManager);
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
                orderTableManager.getOrderTable(order.getOrderTableId()),
                order.getOrderTableId()
        );
    }

    private OrderLineItem createOrderLineItem(final OrderLineItemRequest orderLineItemRequest) {
        return new OrderLineItem(orderLineItemRequest.getMenuId(),
                new OrderQuantity(orderLineItemRequest.getQuantity()),
                orderLineItemRequest.getPrice());
    }
}
