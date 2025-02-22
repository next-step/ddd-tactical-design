package kitchenpos.takeoutorder.application.service;

import kitchenpos.deliveryorder.adapter.out.api.KitchenridersClient;
import kitchenpos.menu.application.port.out.MenuRepository;
import kitchenpos.menu.domain.Menu;
import kitchenpos.takeoutorder.application.port.out.TakeOutOrderRepository;
import kitchenpos.takeoutorder.domain.TakeOutOrder;
import kitchenpos.takeoutorder.domain.TakeOutOrderLineItem;
import kitchenpos.takeoutorder.domain.TakeOutOrderStatus;
import kitchenpos.takeoutorder.domain.TakeOutOrderType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TakeOutOrderService {
    private final TakeOutOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final KitchenridersClient kitchenridersClient;

    public TakeOutOrderService(
            final TakeOutOrderRepository takeOutOrderRepository,
            final MenuRepository menuRepository,
            final KitchenridersClient kitchenridersClient
    ) {
        this.orderRepository = takeOutOrderRepository;
        this.menuRepository = menuRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public TakeOutOrder create(final TakeOutOrder request) {
        final TakeOutOrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
        final List<TakeOutOrderLineItem> takeOutOrderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(takeOutOrderLineItemRequests) || takeOutOrderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
                takeOutOrderLineItemRequests.stream()
                        .map(TakeOutOrderLineItem::getMenuId)
                        .toList()
        );
        if (menus.size() != takeOutOrderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<TakeOutOrderLineItem> takeOutOrderLineItems = new ArrayList<>();
        for (final TakeOutOrderLineItem takeOutOrderLineItemRequest : takeOutOrderLineItemRequests) {
            final long quantity = takeOutOrderLineItemRequest.getQuantity();
            final Menu menu = menuRepository.findById(takeOutOrderLineItemRequest.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(takeOutOrderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final TakeOutOrderLineItem takeOutOrderLineItem = new TakeOutOrderLineItem();
            takeOutOrderLineItem.setMenu(menu);
            takeOutOrderLineItem.setQuantity(quantity);
            takeOutOrderLineItems.add(takeOutOrderLineItem);
        }
        var order = new TakeOutOrder();
        order.setId(UUID.randomUUID());
        order.setType(type);
        order.setStatus(TakeOutOrderStatus.WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(takeOutOrderLineItems);
        return orderRepository.save(order);
    }

    @Transactional
    public TakeOutOrder accept(final UUID orderId) {
        final TakeOutOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != TakeOutOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(TakeOutOrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public TakeOutOrder serve(final UUID orderId) {
        final TakeOutOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != TakeOutOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(TakeOutOrderStatus.SERVED);
        return order;
    }


    @Transactional
    public TakeOutOrder complete(final UUID orderId) {
        final TakeOutOrder order = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        final TakeOutOrderType type = order.getType();
        final TakeOutOrderStatus status = order.getStatus();

        if (status != TakeOutOrderStatus.SERVED) {
            throw new IllegalStateException();
        }

        order.setStatus(TakeOutOrderStatus.COMPLETED);

        return order;
    }

    @Transactional(readOnly = true)
    public List<TakeOutOrder> findAll() {
        return orderRepository.findAll();
    }
}
