package kitchenpos.deliveryorder.application.service;

import kitchenpos.deliveryorder.adapter.out.api.KitchenridersClient;
import kitchenpos.deliveryorder.application.port.out.DeliveryOrderRepository;
import kitchenpos.deliveryorder.domain.DeliveryOrder;
import kitchenpos.deliveryorder.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorder.domain.DeliveryOrderStatus;
import kitchenpos.deliveryorder.domain.DeliveryOrderType;
import kitchenpos.menu.application.port.out.MenuRepository;
import kitchenpos.menu.domain.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeliveryOrderService {
    private final DeliveryOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final KitchenridersClient kitchenridersClient;

    public DeliveryOrderService(

            final DeliveryOrderRepository deliveryOrderRepository,
            final MenuRepository menuRepository,
            final KitchenridersClient kitchenridersClient
    ) {
        this.orderRepository = deliveryOrderRepository;
        this.menuRepository = menuRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public DeliveryOrder create(final DeliveryOrder request) {
        final DeliveryOrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
        final List<DeliveryOrderLineItem> deliveryOrderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(deliveryOrderLineItemRequests) || deliveryOrderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
                deliveryOrderLineItemRequests.stream()
                        .map(DeliveryOrderLineItem::getMenuId)
                        .toList()
        );
        if (menus.size() != deliveryOrderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<DeliveryOrderLineItem> deliveryOrderLineItems = new ArrayList<>();
        for (final DeliveryOrderLineItem deliveryOrderLineItemRequest : deliveryOrderLineItemRequests) {
            final long quantity = deliveryOrderLineItemRequest.getQuantity();

            final Menu menu = menuRepository.findById(deliveryOrderLineItemRequest.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(deliveryOrderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final DeliveryOrderLineItem deliveryOrderLineItem = new DeliveryOrderLineItem();
            deliveryOrderLineItem.setMenu(menu);
            deliveryOrderLineItem.setQuantity(quantity);
            deliveryOrderLineItems.add(deliveryOrderLineItem);
        }
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setId(UUID.randomUUID());
        deliveryOrder.setType(type);
        deliveryOrder.setStatus(DeliveryOrderStatus.WAITING);
        deliveryOrder.setOrderDateTime(LocalDateTime.now());
        deliveryOrder.setOrderLineItems(deliveryOrderLineItems);
        if (type == DeliveryOrderType.DELIVERY) {
            final String deliveryAddress = request.getDeliveryAddress();
            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
                throw new IllegalArgumentException();
            }
            deliveryOrder.setDeliveryAddress(deliveryAddress);
        }
        return orderRepository.save(deliveryOrder);
    }

    @Transactional
    public DeliveryOrder accept(final UUID orderId) {
        final DeliveryOrder deliveryOrder = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (deliveryOrder.getStatus() != DeliveryOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        if (deliveryOrder.getType() == DeliveryOrderType.DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final DeliveryOrderLineItem deliveryOrderLineItem : deliveryOrder.getOrderLineItems()) {
                sum = deliveryOrderLineItem.getMenu()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(deliveryOrderLineItem.getQuantity()));
            }
            kitchenridersClient.requestDelivery(orderId, sum, deliveryOrder.getDeliveryAddress());
        }
        deliveryOrder.setStatus(DeliveryOrderStatus.ACCEPTED);
        return deliveryOrder;
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
        if (order.getType() != DeliveryOrderType.DELIVERY) {
            throw new IllegalStateException();
        }
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
        final DeliveryOrderType type = order.getType();
        final DeliveryOrderStatus status = order.getStatus();
        if (type == DeliveryOrderType.DELIVERY) {
            if (status != DeliveryOrderStatus.DELIVERED) {
                throw new IllegalStateException();
            }
        }
        order.setStatus(DeliveryOrderStatus.COMPLETED);

        return order;
    }

    @Transactional(readOnly = true)
    public List<DeliveryOrder> findAll() {
        return orderRepository.findAll();
    }
}
