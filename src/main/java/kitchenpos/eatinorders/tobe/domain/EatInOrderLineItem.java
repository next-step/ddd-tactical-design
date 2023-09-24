package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.sharedkernel.OrderLineItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem extends OrderLineItem {

    @Column(name = "quantity", nullable = false)
    protected long quantity;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(final Long seq, final long quantity, final UUID menuId, final BigDecimal price) {
        this.seq = seq;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public static EatInOrderLineItem create(final long quantity, final UUID menuId, final long price) {
        return new EatInOrderLineItem(
            new Random().nextLong(),
            quantity,
            menuId,
            BigDecimal.valueOf(price)
        );
    }
}
