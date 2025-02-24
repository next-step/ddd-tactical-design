package kitchenpos.order.deliveryorder.application;

import static kitchenpos.order.common.domain.OrderType.DELIVERY;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.ACCEPTED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.COMPLETED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.DELIVERED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.DELIVERING;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.SERVED;
import static kitchenpos.order.deliveryorder.domain.DeliveryOrderStatus.WAITING;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderLineItem;
import kitchenpos.order.common.domain.OrderRepository;
import kitchenpos.order.common.domain.OrderType;
import kitchenpos.order.deliveryorder.domain.DeliveryOrder;
import kitchenpos.order.deliveryorder.domain.KitchenridersClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultDeliveryOrderService implements DeliveryOrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final KitchenridersClient kitchenridersClient;

    public DefaultDeliveryOrderService(OrderRepository orderRepository, MenuRepository menuRepository,
        KitchenridersClient kitchenridersClient) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public Order create(final Order request) {
        final OrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
            orderLineItemRequests.stream()
                .map(OrderLineItem::getMenuId)
                .toList()
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<OrderLineItem> orderLineItems = new ArrayList<>();
        for (final OrderLineItem orderLineItemRequest : orderLineItemRequests) {
            final long quantity = orderLineItemRequest.getQuantity();
            final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
                .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setMenu(menu);
            orderLineItem.setQuantity(quantity);
            orderLineItems.add(orderLineItem);
        }
        DeliveryOrder order = new DeliveryOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        if (request instanceof DeliveryOrder deliveryOrderRequest) {
            final String deliveryAddress = deliveryOrderRequest.getDeliveryAddress();
            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
                throw new IllegalArgumentException();
            }
            order.setDeliveryAddress(deliveryAddress);
        }
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order accept(final UUID orderId) {
        final DeliveryOrder order = (DeliveryOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != WAITING) {
            throw new IllegalStateException();
        }
        if (order instanceof DeliveryOrder deliveryOrder) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final OrderLineItem orderLineItem : order.getOrderLineItems()) {
                sum = orderLineItem.getMenu()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
            }
            kitchenridersClient.requestDelivery(orderId, sum, deliveryOrder.getDeliveryAddress());
        }
        order.setStatus(ACCEPTED);
        return order;
    }

    @Transactional
    @Override
    public Order serve(final UUID orderId) {
        final DeliveryOrder order = (DeliveryOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(SERVED);
        return order;

    }

    @Transactional
    @Override
    public Order startDelivery(final UUID orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (!(order instanceof DeliveryOrder deliveryOrder)) {
            throw new IllegalStateException();
        }
        if (deliveryOrder.getStatus() != SERVED) {
            throw new IllegalStateException();
        }
        deliveryOrder.setStatus(DELIVERING);
        return order;

    }

    @Transactional
    @Override
    public Order completeDelivery(final UUID orderId) {
        final DeliveryOrder order = (DeliveryOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != DELIVERING) {
            throw new IllegalStateException();
        }
        order.setStatus(DELIVERED);
        return order;
    }

    @Transactional
    @Override
    public Order complete(final UUID orderId) {
        final DeliveryOrder order = (DeliveryOrder) orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final OrderType type = order.getType();
        if (type == DELIVERY) {
            if (order.getStatus() != DELIVERED) {
                throw new IllegalStateException();
            }
        }
        order.setStatus(COMPLETED);
        return order;

    }
}
