package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.NOT_EQUALS_PRICE;
import static kitchenpos.eatinorders.exception.OrderExceptionMessage.ORDER_LINE_ITEM_MENU_NOT_DISPLAY;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private EatInMenu menu;

    @Embedded
    private Quantity quantity;

    @Transient
    private Price price;

    public EatInOrderLineItem() {
    }

    private EatInOrderLineItem(Long seq, EatInMenu eatInMenu, Quantity quantity, Price orderLineItemPrice) {
        if (!eatInMenu.isDisplayed()) {
            throw new IllegalStateException(ORDER_LINE_ITEM_MENU_NOT_DISPLAY);
        }
        if (eatInMenu.getPrice().isNotEqualTo(orderLineItemPrice)) {
            throw new IllegalArgumentException(NOT_EQUALS_PRICE);
        }
        this.seq = seq;
        this.menu = eatInMenu;
        this.quantity = quantity;
        this.price = orderLineItemPrice;
    }

    private EatInOrderLineItem(EatInMenu eatInMenu, Quantity quantity, Price orderLineItemPrice) {
        if (!eatInMenu.isDisplayed()) {
            throw new IllegalStateException(ORDER_LINE_ITEM_MENU_NOT_DISPLAY);
        }
        if (eatInMenu.getPrice().isNotEqualTo(orderLineItemPrice)) {
            throw new IllegalArgumentException(NOT_EQUALS_PRICE);
        }
        this.menu = eatInMenu;
        this.quantity = quantity;
        this.price = orderLineItemPrice;
    }

    public static EatInOrderLineItem create(Long seq, EatInMenu eatInMenu, long quantity, Price createdOrderLineItemPrice) {
        return new EatInOrderLineItem(seq, eatInMenu, Quantity.create(quantity), createdOrderLineItemPrice);
    }

    public static EatInOrderLineItem create(EatInMenu eatInMenu, long quantity, Price createdOrderLineItemPrice) {
        return new EatInOrderLineItem(eatInMenu, Quantity.create(quantity), createdOrderLineItemPrice);
    }

    public Long getSeq() {
        return seq;
    }

    public EatInMenu getMenu() {
        return menu;
    }

    public UUID getMenuId() {
        return menu.getId();
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public long getQuantityValue() {
        return quantity.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItem that = (EatInOrderLineItem) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
