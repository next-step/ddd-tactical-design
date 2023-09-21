package kitchenpos.deliveryorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Embeddable
public class DeliveryOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<DeliveryOrderLineItem> deliveryOrderLineItemList;

    public DeliveryOrderLineItems(final List<DeliveryOrderLineItem> deliveryOrderLineItemList, final Map<UUID, Menu> menuMap) {
        deliveryOrderLineItemList.forEach(orderLineItem -> {
            Menu menu = menuMap.get(orderLineItem.getMenuId());
            validateMenu(menu);
            validateMenuPrice(orderLineItem, menu);
        });
        this.deliveryOrderLineItemList = deliveryOrderLineItemList;
    }

    protected DeliveryOrderLineItems() {

    }

    private void validateMenu(final Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateMenuPrice(final DeliveryOrderLineItem deliveryOrderLineItem, final Menu menu) {
        if (menu.getBigDecimalPrice().compareTo(deliveryOrderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
    }

    public List<DeliveryOrderLineItem> getOrderLineItemList() {
        return deliveryOrderLineItemList;
    }

    public BigDecimal getSumOfOrderLineItemPrice(List<Menu> menus) {
        return deliveryOrderLineItemList.stream()
            .map(orderLineItem -> {
                Menu menu = menus.stream().findFirst().orElseThrow();
                return menu.multiplyPrice(BigDecimal.valueOf(orderLineItem.getLongQuantity()));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
