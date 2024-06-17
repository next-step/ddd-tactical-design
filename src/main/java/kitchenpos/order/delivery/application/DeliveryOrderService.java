package kitchenpos.order.delivery.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.delivery.domain.DeliveryOrder;
import kitchenpos.order.delivery.domain.DeliveryOrderLineItem;
import kitchenpos.order.delivery.domain.DeliveryOrderRepository;
import kitchenpos.order.delivery.domain.DeliveryOrderStatus;
import kitchenpos.order.delivery.infra.KitchenridersClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryOrderService {

    private final DeliveryOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final KitchenridersClient kitchenridersClient;

    public DeliveryOrderService(
        final DeliveryOrderRepository orderRepository,
        final MenuRepository menuRepository,
        final KitchenridersClient kitchenridersClient
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public DeliveryOrder create(final DeliveryOrder request) {
        final List<DeliveryOrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
            orderLineItemRequests.stream()
                .map(DeliveryOrderLineItem::getMenuId)
                .toList()
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<DeliveryOrderLineItem> orderLineItems = new ArrayList<>();
        for (final DeliveryOrderLineItem orderLineItemRequest : orderLineItemRequests) {
            final long quantity = orderLineItemRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
                .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final DeliveryOrderLineItem orderLineItem = new DeliveryOrderLineItem();
            orderLineItem.setMenu(menu);
            orderLineItem.setQuantity(quantity);
            orderLineItems.add(orderLineItem);
        }
        DeliveryOrder order = new DeliveryOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(DeliveryOrderStatus.WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        final String deliveryAddress = request.getDeliveryAddress();
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
        order.setDeliveryAddress(deliveryAddress);
        return orderRepository.save(order);
    }

    @Transactional
    public DeliveryOrder accept(final UUID orderId) {
        final DeliveryOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != DeliveryOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (final DeliveryOrderLineItem orderLineItem : order.getOrderLineItems()) {
            sum = orderLineItem.getMenu()
                .getPrice()
                .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
        }
        kitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
        order.setStatus(DeliveryOrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public DeliveryOrder serve(final UUID orderId) {
        final DeliveryOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != DeliveryOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(DeliveryOrderStatus.SERVED);
        return order;
    }

    @Transactional
    public DeliveryOrder startDelivery(final UUID orderId) {
        final DeliveryOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != DeliveryOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setStatus(DeliveryOrderStatus.DELIVERING);
        return order;
    }

    @Transactional
    public DeliveryOrder completeDelivery(final UUID orderId) {
        final DeliveryOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != DeliveryOrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        order.setStatus(DeliveryOrderStatus.DELIVERED);
        return order;
    }

    @Transactional
    public DeliveryOrder complete(final UUID orderId) {
        final DeliveryOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final DeliveryOrderStatus status = order.getStatus();
        if (status != DeliveryOrderStatus.DELIVERED) {
            throw new IllegalStateException();
        }
        order.setStatus(DeliveryOrderStatus.COMPLETED);
        return order;
    }

    @Transactional(readOnly = true)
    public List<DeliveryOrder> findAll() {
        return orderRepository.findAll();
    }
}
