package kitchenpos.takeoutorder.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.takeoutorder.domain.TakeoutOrder;
import kitchenpos.takeoutorder.domain.TakeoutOrderLineItem;
import kitchenpos.takeoutorder.domain.TakeoutOrderRepository;
import kitchenpos.takeoutorder.domain.TakeoutOrderStatus;
import kitchenpos.takeoutorder.domain.TakeoutOrderType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeoutOrderService {
    private final TakeoutOrderRepository takeoutOrderRepository;
    private final MenuRepository menuRepository;

    public TakeoutOrderService(
        final TakeoutOrderRepository takeoutOrderRepository,
        final MenuRepository menuRepository
    ) {
        this.takeoutOrderRepository = takeoutOrderRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public TakeoutOrder create(final TakeoutOrder request) {
        final TakeoutOrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
        final List<TakeoutOrderLineItem> takeoutOrderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(takeoutOrderLineItemRequests)
            || takeoutOrderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
            takeoutOrderLineItemRequests.stream()
                .map(TakeoutOrderLineItem::getMenuId)
                .collect(Collectors.toList())
        );
        if (menus.size() != takeoutOrderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<TakeoutOrderLineItem> takeoutOrderLineItems = new ArrayList<>();
        for (final TakeoutOrderLineItem takeoutOrderLineItemRequest : takeoutOrderLineItemRequests) {
            final long quantity = takeoutOrderLineItemRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Menu menu = menuRepository.findById(takeoutOrderLineItemRequest.getMenuId())
                .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(takeoutOrderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final TakeoutOrderLineItem takeoutOrderLineItem = new TakeoutOrderLineItem();
            takeoutOrderLineItem.setMenu(menu);
            takeoutOrderLineItem.setQuantity(quantity);
            takeoutOrderLineItems.add(takeoutOrderLineItem);
        }
        TakeoutOrder takeoutOrder = new TakeoutOrder();
        takeoutOrder.setId(UUID.randomUUID());
        takeoutOrder.setType(type);
        takeoutOrder.setStatus(TakeoutOrderStatus.WAITING);
        takeoutOrder.setOrderDateTime(LocalDateTime.now());
        takeoutOrder.setOrderLineItems(takeoutOrderLineItems);
        return takeoutOrderRepository.save(takeoutOrder);
    }

    @Transactional
    public TakeoutOrder accept(final UUID orderId) {
        final TakeoutOrder takeoutOrder = takeoutOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (takeoutOrder.getStatus() != TakeoutOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        takeoutOrder.setStatus(TakeoutOrderStatus.ACCEPTED);
        return takeoutOrder;
    }

    @Transactional
    public TakeoutOrder serve(final UUID orderId) {
        final TakeoutOrder takeoutOrder = takeoutOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (takeoutOrder.getStatus() != TakeoutOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        takeoutOrder.setStatus(TakeoutOrderStatus.SERVED);
        return takeoutOrder;
    }

    @Transactional
    public TakeoutOrder complete(final UUID orderId) {
        final TakeoutOrder takeoutOrder = takeoutOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final TakeoutOrderStatus status = takeoutOrder.getStatus();
        if (status != TakeoutOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        takeoutOrder.setStatus(TakeoutOrderStatus.COMPLETED);
        return takeoutOrder;
    }

    @Transactional(readOnly = true)
    public List<TakeoutOrder> findAll() {
        return takeoutOrderRepository.findAll();
    }
}
