package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static kitchenpos.eatinorders.tobe.domain.OrderType.EAT_IN;

@Embeddable
public class OrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() {}

    public OrderLineItems(final List<OrderLineItem> orderLineItems, final OrderType orderType, final MenuTranslator menuTranslator) {
        validate(orderLineItems, orderType, menuTranslator);
        this.orderLineItems = Collections.unmodifiableList(orderLineItems);
    }

    List<OrderLineItem> getOrderLineItems() {
        return new ArrayList<>(orderLineItems);
    }

    private void validate(final List<OrderLineItem> orderLineItems, final OrderType orderType, final MenuTranslator menuTranslator) {
        final int menuSize = menuTranslator.getMenus(orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList())
        ).size();
        if (menuSize != orderLineItems.size()) {
            throw new IllegalArgumentException("부적절한 menu 는 주문하할 수 없습니다.");
        }
        if (orderType != EAT_IN) {
            orderLineItems.stream()
                    .map(OrderLineItem::getQuantity)
                    .forEach(this::validateQuantity);
        }
        orderLineItems.forEach(menuTranslator::validateOrderLineItem);
    }

    private void validateQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("매장 식사 주문은 수량이 1개 이상이어야 합니다.");
        }
    }
}
