package kitchenpos.deliveryorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItemList;

    public OrderLineItems(final List<OrderLineItem> orderLineItemList, final Map<UUID, Menu> menuMap) {
        orderLineItemList.forEach(orderLineItem -> {
            Menu menu = menuMap.get(orderLineItem.getMenuId());
            validateMenu(menu);
            validateMenuPrice(orderLineItem, menu);
        });
        this.orderLineItemList = orderLineItemList;
    }

    protected OrderLineItems() {

    }

    private void validateMenu(final Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateMenuPrice(final OrderLineItem orderLineItem, final Menu menu) {
        if (menu.getBigDecimalPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
    }

    public List<OrderLineItem> getOrderLineItemList() {
        return orderLineItemList;
    }

    public BigDecimal getSumOfOrderLineItemPrice(List<Menu> menus) {
        return orderLineItemList.stream()
            .map(orderLineItem -> {
                Menu menu = menus.stream().findFirst().orElseThrow();
                return menu.multiplyPrice(BigDecimal.valueOf(orderLineItem.getLongQuantity()));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
