package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Embeddable
public class EatInOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<EatInOrderLineItem> eatInOrderLineItemList;

    public EatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItemList, final Map<UUID, Menu> menuMap) {
        eatInOrderLineItemList.forEach(orderLineItem -> {
            Menu menu = menuMap.get(orderLineItem.getMenuId());
            validateMenu(menu);
            validateMenuPrice(orderLineItem, menu);
        });
        this.eatInOrderLineItemList = eatInOrderLineItemList;
    }

    protected EatInOrderLineItems() {

    }

    private void validateMenu(final Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateMenuPrice(final EatInOrderLineItem eatInOrderLineItem, final Menu menu) {
        if (menu.getBigDecimalPrice().compareTo(eatInOrderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException();
        }
    }

    public List<EatInOrderLineItem> getOrderLineItemList() {
        return eatInOrderLineItemList;
    }
}
