package kitchenpos.eatinorders.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.MenuLoader;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderLineItemException;

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

    public EatInOrderLineItem(UUID menuId, EatInOrderLineItemQuantity quantity, Price price, MenuLoader menuLoader) {

        OrderedMenu orderedMenu = menuLoader.findMenuById(menuId);

        if (!orderedMenu.getMenuPrice().equals(price)) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_PRICE_EQUAL_MENU_PRICE);
        }

        this.menu = orderedMenu;
        this.quantity = quantity;
        this.price = price;
    }

    public EatInOrderLineItem(UUID menuId, long quantity, Price price, MenuLoader policy) {
        this(menuId,
             new EatInOrderLineItemQuantity(quantity),
             price,
             policy);
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
