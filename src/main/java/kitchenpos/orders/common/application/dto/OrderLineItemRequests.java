package kitchenpos.orders.common.application.dto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;

public class OrderLineItemRequests {

    private final List<OrderLineItemRequest> orderLineItems;

    public OrderLineItemRequests(List<OrderLineItemRequest> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.orderLineItems = orderLineItems;
    }

    public List<UUID> getMenuIds() {
        return orderLineItems.stream().map(OrderLineItemRequest::getMenuId).toList();
    }

    public List<OrderLineItem> toOrderLineItems(List<Menu> menus, OrderType orderType) {
        return orderLineItems.stream()
                .map(orderLineItem -> orderLineItem.toStore(findMenu(menus, orderLineItem),
                        orderType)).toList();
    }

    private Menu findMenu(List<Menu> menus, OrderLineItemRequest orderLineItem) {
        return menus.stream().filter(menu -> menu.getId().equals(orderLineItem.getMenuId()))
                .findFirst().orElseThrow((NoSuchElementException::new));
    }
}
