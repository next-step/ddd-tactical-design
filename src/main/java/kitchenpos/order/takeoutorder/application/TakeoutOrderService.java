package kitchenpos.order.takeoutorder.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.takeoutorder.domain.TakeOutOrder;
import kitchenpos.order.takeoutorder.domain.TakeOutOrderLineItem;
import kitchenpos.order.takeoutorder.domain.TakeOutOrderRepository;
import kitchenpos.order.takeoutorder.domain.TakeOutOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeoutOrderService {

    private final TakeOutOrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public TakeoutOrderService(
        final TakeOutOrderRepository orderRepository,
        final MenuRepository menuRepository
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public TakeOutOrder create(final TakeOutOrder request) {
        final var orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
            orderLineItemRequests.stream()
                .map(TakeOutOrderLineItem::getMenuId)
                .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<TakeOutOrderLineItem> orderLineItems = new ArrayList<>();
        for (final var orderLineItemRequest : orderLineItemRequests) {
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
            final TakeOutOrderLineItem orderLineItem = new TakeOutOrderLineItem();
            orderLineItem.setMenu(menu);
            orderLineItem.setQuantity(quantity);
            orderLineItems.add(orderLineItem);
        }
        TakeOutOrder takeOutOrder = new TakeOutOrder();
        takeOutOrder.setId(UUID.randomUUID());
        takeOutOrder.setStatus(TakeOutOrderStatus.WAITING);
        takeOutOrder.setOrderDateTime(LocalDateTime.now());
        takeOutOrder.setOrderLineItems(orderLineItems);
        return orderRepository.save(takeOutOrder);
    }

    @Transactional
    public TakeOutOrder accept(final UUID orderId) {
        final TakeOutOrder takeOutOrder = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (takeOutOrder.getStatus() != TakeOutOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        takeOutOrder.setStatus(TakeOutOrderStatus.ACCEPTED);
        return takeOutOrder;
    }

    @Transactional
    public TakeOutOrder serve(final UUID orderId) {
        final TakeOutOrder takeOutOrder = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (takeOutOrder.getStatus() != TakeOutOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        takeOutOrder.setStatus(TakeOutOrderStatus.SERVED);
        return takeOutOrder;
    }

    @Transactional
    public TakeOutOrder complete(final UUID orderId) {
        final TakeOutOrder takeOutOrder = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final TakeOutOrderStatus status = takeOutOrder.getStatus();
        if (status != TakeOutOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        takeOutOrder.setStatus(TakeOutOrderStatus.COMPLETED);
        return takeOutOrder;
    }

    @Transactional(readOnly = true)
    public List<TakeOutOrder> findAll() {
        return orderRepository.findAll();
    }
}
