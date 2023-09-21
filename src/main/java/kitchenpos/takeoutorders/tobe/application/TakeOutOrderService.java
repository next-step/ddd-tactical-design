package kitchenpos.takeoutorders.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.takeoutorders.tobe.application.dto.OrderLineItemRequest;
import kitchenpos.takeoutorders.tobe.application.dto.TakeOutOrderRequest;
import kitchenpos.takeoutorders.tobe.domain.OrderLineItem;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrder;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TakeOutOrderService {
    private final TakeOutOrderRepository takeOutOrderRepository;
    private final MenuRepository menuRepository;

    public TakeOutOrderService(TakeOutOrderRepository takeOutOrderRepository, MenuRepository menuRepository) {
        this.takeOutOrderRepository = takeOutOrderRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public TakeOutOrder create(final TakeOutOrderRequest request) {
        request.validate();

        final List<OrderLineItem> orderLineItems = request.getOrderLineItems()
            .stream()
            .map(OrderLineItemRequest::toOrderLineItem)
            .collect(Collectors.toList());
        final List<Menu> menus = findMenus(orderLineItems);

        return TakeOutOrder.create(
            menus,
            orderLineItems
        );
    }

    @Transactional
    public TakeOutOrder accept(final UUID orderId) {
        final TakeOutOrder order = findOrder(orderId);
        order.accept();
        return order;
    }

    @Transactional
    public TakeOutOrder serve(final UUID orderId) {
        final TakeOutOrder order = findOrder(orderId);
        order.serve();
        return order;
    }

    @Transactional
    public TakeOutOrder complete(final UUID orderId) {
        final TakeOutOrder order = findOrder(orderId);
        order.complete();
        return order;
    }

    @Transactional(readOnly = true)
    public List<TakeOutOrder> findAll() {
        return takeOutOrderRepository.findAll();
    }

    private TakeOutOrder findOrder(final UUID orderId) {
        return takeOutOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
    }

    private List<Menu> findMenus(final List<OrderLineItem> orderLineItems) {
        return menuRepository.findAllByIdIn(
            orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList())
        );
    }
}
