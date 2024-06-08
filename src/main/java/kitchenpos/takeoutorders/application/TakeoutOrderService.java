package kitchenpos.takeoutorders.application;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.domain.OrderLineItem;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderRepository;
import kitchenpos.takeoutorders.domain.TakeoutOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class TakeoutOrderService {
    private final TakeoutOrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public TakeoutOrderService(
        final TakeoutOrderRepository orderRepository,
        final MenuRepository menuRepository
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public TakeoutOrder create(final TakeoutOrder request) {
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
            final OrderLineItem orderLineItem = new OrderLineItem(menu.getId(), menu.getPrice(), quantity);
            orderLineItems.add(orderLineItem);
        }
        TakeoutOrder order = new TakeoutOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(TakeoutOrderStatus.WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        return orderRepository.save(order);
    }

    @Transactional
    public TakeoutOrder accept(final UUID orderId) {
        final TakeoutOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != TakeoutOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(TakeoutOrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public TakeoutOrder serve(final UUID orderId) {
        final TakeoutOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != TakeoutOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(TakeoutOrderStatus.SERVED);
        return order;
    }

    @Transactional
    public TakeoutOrder complete(final UUID orderId) {
        final TakeoutOrder order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final TakeoutOrderStatus status = order.getStatus();
            if (status != TakeoutOrderStatus.SERVED) {
                throw new IllegalStateException();
            }
        order.setStatus(TakeoutOrderStatus.COMPLETED);
        return order;
    }

    @Transactional(readOnly = true)
    public List<TakeoutOrder> findAll() {
        return orderRepository.findAll();
    }
}
