package kitchenpos.eatinorders.tobe.domain.model;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderLineItems {

    private final List<OrderLineItem> orderLineItems;

    public OrderLineItems(final List<OrderLineItem> orderLineItems) {
        validate(orderLineItems);

        this.orderLineItems = orderLineItems;
    }

    private void validate(final List<OrderLineItem> orderLineItems) {
        if (orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 1개 이상이어야 합니다.");
        }
    }

    public List<UUID> getMenuIds() {
        return orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList());
    }

    public boolean canBeCompleted() {
        return orderLineItems.stream()
                .collect(Collectors.groupingBy(OrderLineItem::getMenuId))
                .values()
                .stream()
                .map(orderLineItems -> orderLineItems.stream()
                        .mapToLong(OrderLineItem::getQuantity)
                        .reduce(0L, Long::sum))
                .allMatch(quantity -> quantity >= 0L);
    }

    public void validateOrderPrice(final OrderMenus orderMenus) {
        orderLineItems.forEach(orderLineItem -> orderLineItem.validateOrderPrice(
                orderMenus.getOrderMenuByMenuId(orderLineItem.getMenuId())
        ));
    }
}
