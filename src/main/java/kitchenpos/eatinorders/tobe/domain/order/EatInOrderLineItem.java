package kitchenpos.eatinorders.tobe.domain.order;

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

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private UUID menuId;

    @Embedded
    private EatInOrderLineItemQuantity quantity;

    @Embedded
    private EatInOrderLineItemPrice price;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(UUID menuId, EatInOrderLineItemQuantity quantity, EatInOrderLineItemPrice price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public static EatInOrderLineItem of(UUID menuId, EatInOrderLineItemQuantity quantity, EatInOrderLineItemPrice price, EatInMenuClient eatInMenuClient) {
        validateEatInOrderLineItem(menuId, price, eatInMenuClient);
        return new EatInOrderLineItem(menuId, quantity, price);
    }

    private static void validateEatInOrderLineItem(UUID menuId, EatInOrderLineItemPrice price, EatInMenuClient eatInMenuClient) {
        Menu menu = eatInMenuClient.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다. menuId: " + menuId));

        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }

        if (menu.getPrice().compareTo(price.getEatInOrderLineItemPrice()) != 0) {
            throw new IllegalArgumentException();
        }
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity.getQuantity();
    }

    public BigDecimal getPrice() {
        return price.getEatInOrderLineItemPrice();
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
