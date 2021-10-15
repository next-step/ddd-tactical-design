package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderLineItems {

    private static final BigDecimal COMPLETABLE_AMOUNT_STANDARD = BigDecimal.ZERO;

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
                        .map(OrderLineItem::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .allMatch(amount -> amount.compareTo(COMPLETABLE_AMOUNT_STANDARD) >= 0);
    }

    public void validateOrderPrice(final Map<UUID, Price> menuPriceMap) {
        orderLineItems.forEach(orderLineItem -> orderLineItem.validateOrderPrice(menuPriceMap.get(orderLineItem.getMenuId())));
    }
}
