package kitchenpos.eatinorders.tobe.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.infra.OrderMenuAdaptor;
import kitchenpos.eatinorders.tobe.ui.OrderForm;
import kitchenpos.eatinorders.tobe.ui.OrderLineItemForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TobeOrderService {
    private final TobeOrderRepository orderRepository;
    private final OrderMenuAdaptor orderMenuAdaptor;
    private final OrderLineFactory orderLineFactory;
    private final KitchenridersClient kitchenridersClient;

    public TobeOrderService(
        final TobeOrderRepository orderRepository,
        final OrderMenuAdaptor orderMenuAdaptor,
        final OrderLineFactory orderLineFactory,
        final KitchenridersClient kitchenridersClient
    ) {
        this.orderRepository = orderRepository;
        this.orderMenuAdaptor = orderMenuAdaptor;
        this.orderLineFactory = orderLineFactory;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public OrderForm create(final OrderForm request) {
        ValidationOrderService.validateOrderType(request.getType());
        ValidationOrderService.validateOrderLineItem(request.getOrderLineItems());

        final List<OrderLineItemForm> orderLineItemRequests = request.getOrderLineItems();
        final List<Menu> menus = callMenuAdaptor(orderLineItemRequests);

        ValidationOrderService.menuSizeEqualsOrderLineItemSize(menus, orderLineItemRequests);

        final TobeOrderLineItems orderLineItems = orderLineFactory.findOrderLineItems(request, menus);
        final TobeOrder order = new TobeOrder(
                request.getType(),
                orderLineItems,
                request.getDeliveryAddress(),
                request.getOrderTableId()
        );

        orderRepository.save(order);
        return OrderForm.of(order);
    }

    private List<Menu> callMenuAdaptor(List<OrderLineItemForm> orderLineItemRequests) {
        return orderMenuAdaptor.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItemForm::getMenuId)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public OrderForm accept(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.accept(kitchenridersClient);
        return OrderForm.of(order);
    }

    @Transactional
    public OrderForm serve(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.server();
        return OrderForm.of(order);
    }

    @Transactional
    public OrderForm startDelivery(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.startDelivery();
        return OrderForm.of(order);
    }

    @Transactional
    public OrderForm completeDelivery(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.completeDelivery();
        return OrderForm.of(order);
    }

    @Transactional
    public OrderForm complete(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        order.complete();
        return OrderForm.of(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderForm> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderForm::of)
                .collect(Collectors.toList());
    }

    public boolean isOrderComplete(UUID orderTableId) {
        return orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED);
    }
}
