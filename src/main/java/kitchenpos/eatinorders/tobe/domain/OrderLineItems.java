package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.domain.Menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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
        final List<Menu> menus = menuTranslator.getMenus(orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList()));
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException("부적절한 menu 는 주문하할 수 없습니다.");
        }
        if (orderType != EAT_IN) {
            orderLineItems.stream()
                    .map(OrderLineItem::getQuantity)
                    .forEach(this::validateQuantity);
        }
        orderLineItems.forEach(item -> validateMenu(item, menuTranslator));
    }

    private void validateQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("매장 식사 주문은 수량이 1개 이상이어야 합니다.");
        }
    }

    private void validateMenu(final OrderLineItem orderLineItem, final MenuTranslator menuTranslator) {
        final UUID menuId = orderLineItem.getMenuId();
        final BigDecimal orderLineItemPrice = orderLineItem.getPrice();
        final Menu menu = menuTranslator.getMenu(menuId, orderLineItemPrice);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴는 주문할 수 없습니다.");
        }
        if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException("주문 항목의 가격과 메뉴의 가격은 같아야 합니다.");
        }
    }
}
