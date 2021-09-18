package kitchenpos.eatinorders.tobe.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.dto.CreateOrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.*;
import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;
import static kitchenpos.eatinorders.tobe.domain.OrderType.EAT_IN;

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
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        if (order.getType() == DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final OrderLineItem orderLineItem : order.getOrderLineItems()) {
                final UUID menuId = orderLineItem.getMenuId();
                final BigDecimal orderLineItemPrice = orderLineItem.getPrice();
                sum = menuTranslator.getMenu(menuId, orderLineItemPrice)
                        .getPrice()
                        .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
            }
            kitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
        }
        order.changeStatus(ACCEPTED);
        return order;
    }

    @Transactional
    public Order serve(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != ACCEPTED) {
            throw new IllegalStateException();
        }
        order.changeStatus(SERVED);
        return order;
    }

    @Transactional
    public Order startDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getType() != DELIVERY) {
            throw new IllegalStateException();
        }
        if (order.getStatus() != SERVED) {
            throw new IllegalStateException();
        }
        order.changeStatus(DELIVERING);
        return order;
    }

    @Transactional
    public Order completeDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        order.changeStatus(DELIVERED);
        return order;
    }

    @Transactional
    public Order complete(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        final OrderType type = order.getType();
        final OrderStatus status = order.getStatus();
        if (type == DELIVERY) {
            if (status != OrderStatus.DELIVERED) {
                throw new IllegalStateException();
            }
        }
        if (type == OrderType.TAKEOUT || type == EAT_IN) {
            if (status != SERVED) {
                throw new IllegalStateException();
            }
        }
        order.changeStatus(COMPLETED);
        if (type == EAT_IN) {
            orderTableTranslator.clearOrderTable(order.getOrderTableId());
        }
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
