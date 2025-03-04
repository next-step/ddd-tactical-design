package kitchenpos.eatinorders.tobe.domain.common;

import jakarta.persistence.*;
import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemsException;
import kitchenpos.menus.tobe.domain.MenuId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() {
    }

    public OrderLineItems(OrderLineItem... orderLineItems) {
        this(Arrays.stream(orderLineItems).toList());
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new InvalidOrderLineItemsException("주문 내역이 없습니다");
        }
        this.orderLineItems = orderLineItems;
    }

    public int size() {
        return orderLineItems.size();
    }

    public Price totalPrice() {
        return orderLineItems.stream()
                .map(OrderLineItem::amount)
                .reduce(Price.ZERO(), Price::add);
    }

    public List<MenuId> menuIds() {
        return orderLineItems.stream()
                .map(OrderLineItem::menuId)
                .toList();
    }

    public boolean isSizeMismatch(int otherSize) {
        return size() != otherSize;
    }

    public boolean hasDifferentPrice(MenuId id, Price price) {
        return orderLineItems.stream()
                .noneMatch(item -> item.isSameMenuPrice(id, price));
    }

    public List<OrderLineItem> getOrderLineItems() {
        return new ArrayList<>(orderLineItems);
    }
}
