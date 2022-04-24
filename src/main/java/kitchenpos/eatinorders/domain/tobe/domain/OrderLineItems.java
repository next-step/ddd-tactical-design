package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;

import javax.persistence.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<TobeOrderLineItem> orderLineItems;

    public OrderLineItems(List<TobeOrderLineItem> orderLineItems) {
        if(Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.orderLineItems = orderLineItems;
    }

    protected OrderLineItems() {

    }

    public int size() {
        return orderLineItems.size();
    }

    public boolean isEmpty() {
        return orderLineItems.isEmpty();
    }

    public List<MenuId> getMenuIds() {
        return orderLineItems.stream()
                .map(TobeOrderLineItem::getMenuId)
                .collect(Collectors.toList());
    }

    public void validateMenus(List<TobeMenu> menus) {
        if(menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException();
        }
        for(TobeMenu menu : menus) {
            if(!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            TobeOrderLineItem item = getOrderLineItemByMenuId(menu.getId())
                    .orElseThrow(NoSuchElementException::new);
            if(!item.getPrice().equals(menu.getPrice())) {
                throw new IllegalStateException();
            }
        }
    }

    public List<TobeOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    private Optional<TobeOrderLineItem> getOrderLineItemByMenuId(MenuId id) {
        return orderLineItems.stream().filter(o->o.getMenuId().equals(id)).findFirst();
    }
}
