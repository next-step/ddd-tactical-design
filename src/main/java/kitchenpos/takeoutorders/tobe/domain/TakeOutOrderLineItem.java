package kitchenpos.takeoutorders.tobe.domain;

import kitchenpos.sharedkernel.OrderLineItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Table(name = "take_out_order_line_item")
@Entity
public class TakeOutOrderLineItem extends OrderLineItem {

    @Column(name = "quantity", nullable = false)
    protected long quantity;

    protected TakeOutOrderLineItem() {
    }

    public TakeOutOrderLineItem(final Long seq, final long quantity, final UUID menuId, final BigDecimal price) {
        this.seq = seq;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public static TakeOutOrderLineItem create(final long quantity, final UUID menuId, final long price) {
        return new TakeOutOrderLineItem(
            new Random().nextLong(),
            quantity,
            menuId,
            BigDecimal.valueOf(price)
        );
    }
}
