package kitchenpos.eatinorders.domain;

import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long seq;

    @Embedded
    private MenuId menuId;

    @Embedded
    private EatInOrderLineItemQuantity quantity;

    @Embedded
    private Price price;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(MenuId menuId, EatInOrderLineItemQuantity quantity, Price price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public EatInOrderLineItem(MenuId menuId, long quantity, Price price) {
        this.menuId = menuId;
        this.quantity = new EatInOrderLineItemQuantity(quantity);
        this.price = price;
    }

    public long getSeqValue() {
        return this.seq;
    }

    public long getQuantityValue() {
        return this.quantity.getValue();
    }

    public UUID getMenuIdValue() {
        return menuId.getValue();
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }
}
