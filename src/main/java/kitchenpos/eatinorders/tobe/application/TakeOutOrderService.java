package kitchenpos.eatinorders.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.handler.DomainExceptionHandler;
import kitchenpos.eatinorders.tobe.domain.model.Menu;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderType;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrder;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.repository.TakeOutOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeOutOrderService {

    private final TakeOutOrderRepository takeOutOrderRepository;
    private final MenuRepository menuRepository;
    private final DomainExceptionHandler domainExceptionHandler;

    public TakeOutOrderService(TakeOutOrderRepository takeOutOrderRepository, MenuRepository menuRepository, DomainExceptionHandler domainExceptionHandler) {
        this.takeOutOrderRepository = takeOutOrderRepository;
        this.menuRepository = menuRepository;
        this.domainExceptionHandler = domainExceptionHandler;
    }

    @Transactional
    public TakeOutOrder create(final TakeOutOrder request) {
        validateOrderLineItemRequests(request.getOrderLineItems());

        TakeOutOrder takeOutOrder = new TakeOutOrder(request.getOrderLineItems());

        return takeOutOrderRepository.save(takeOutOrder);
    }

    @Transactional
    public TakeOutOrder accept(final UUID orderId) {
        final TakeOutOrder takeOutOrder = takeOutOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        takeOutOrder.accept(domainExceptionHandler);

        return takeOutOrder;
    }

    @Transactional
    public TakeOutOrder serve(final UUID orderId) {
        final TakeOutOrder takeOutOrder = takeOutOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        takeOutOrder.serve(domainExceptionHandler);

        return takeOutOrder;
    }

    @Transactional
    public TakeOutOrder complete(final UUID orderId) {
        final TakeOutOrder takeOutOrder = takeOutOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);

        takeOutOrder.complete(domainExceptionHandler);

        return takeOutOrder;
    }

    @Transactional(readOnly = true)
    public List<TakeOutOrder> findAll() {
        return takeOutOrderRepository.findAll();
    }

    private void validateOrderLineItemRequests(List<OrderLineItem> orderLineItemRequests) {
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItem::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }

        validateMenuDisplayed(menus);
        validateOrderLineItemRequestQuantityAndPrice(orderLineItemRequests, menus);
    }

    private void validateMenuDisplayed(List<Menu> menus) {
        if (menus.stream().anyMatch(menu -> !menu.isDisplayed())) {
            throw new IllegalStateException();
        }
    }

    private void validateOrderLineItemRequestQuantityAndPrice(List<OrderLineItem> orderLineItemRequests, List<Menu> menus) {
        orderLineItemRequests.forEach(orderLineItem -> {
            if (orderLineItem.getQuantity() < 0) {
                throw new IllegalArgumentException();
            }

            final Optional<Menu> matchedMenu = menus.stream()
                    .filter(menu -> orderLineItem.getMenuId().equals(menu.getId()))
                    .findFirst();

            matchedMenu.orElseThrow(IllegalArgumentException::new);

            if (matchedMenu.get().getPrice().compareTo(orderLineItem.getMenuPrice()) != 0) {
                throw new IllegalArgumentException();
            }
        });
    }
}
