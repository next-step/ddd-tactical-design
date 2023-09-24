package kitchenpos.orders.eatinorders.domain;

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
    private OrderedMenu menu;

    @Embedded
    private EatInOrderLineItemQuantity quantity;

    @Embedded
    private Price price;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(EatInOrderLineItemQuantity quantity, Price price, OrderedMenu orderedMenu) {
        this.menu = orderedMenu;
        this.quantity = quantity;
        this.price = price;
    }

    public EatInOrderLineItem(long quantity, Price price, OrderedMenu orderedMenu) {
        this(new EatInOrderLineItemQuantity(quantity),
                price,
                orderedMenu);
    }

    public long getSeqValue() {
        return this.seq;
    }

    public long getQuantityValue() {
        return this.quantity.getValue();
    }

    public UUID getMenuIdValue() {
        return menu.getId();
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public Price getPrice() {
        return price;
    }
}
