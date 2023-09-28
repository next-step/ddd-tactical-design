package kitchenpos.deliveryorders.domain;

import kitchenpos.menus.tobe.domain.menu.Menu;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Table(name = "delivery_order_line_item")
@Entity
public class DeliveryOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private UUID menuId;

    @Embedded
    private DeliveryOrderLineItemQuantity quantity;

    @Embedded
    private DeliveryOrderLineItemPrice price;

    protected DeliveryOrderLineItem() {
    }

    public DeliveryOrderLineItem(UUID menuId, DeliveryOrderLineItemQuantity quantity, DeliveryOrderLineItemPrice price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public static DeliveryOrderLineItem of(UUID menuId, DeliveryOrderLineItemQuantity quantity, DeliveryOrderLineItemPrice price, MenuClient menuClient) {
        validateDeliveryOrderLineItem(menuId, price, menuClient);
        return new DeliveryOrderLineItem(menuId, quantity, price);
    }

    private static void validateDeliveryOrderLineItem(UUID menuId, DeliveryOrderLineItemPrice price, MenuClient menuClient) {
        Menu menu = menuClient.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다. menuId: " + menuId));

        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }

        if (menu.getPrice().compareTo(price.getDeliveryOrderLineItemPrice()) != 0) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity.getQuantity();
    }

    public BigDecimal getPrice() {
        return price.getDeliveryOrderLineItemPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryOrderLineItem that = (DeliveryOrderLineItem) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
