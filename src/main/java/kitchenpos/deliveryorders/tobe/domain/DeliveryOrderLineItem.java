package kitchenpos.deliveryorders.tobe.domain;

import kitchenpos.sharedkernel.OrderLineItem;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Table(name = "delvery_order_line_item")
@Entity
public class DeliveryOrderLineItem extends OrderLineItem {

    @Embedded
    @Column(name = "quantity", nullable = false)
    private DeliveryOrderLineItemQuantity quantity;

    protected DeliveryOrderLineItem() {
    }

    public DeliveryOrderLineItem(
        final Long seq,
        final DeliveryOrderLineItemQuantity quantity,
        final UUID menuId,
        final BigDecimal price
    ) {
        this.seq = seq;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public static DeliveryOrderLineItem create(
        final long quantity,
        final UUID menuId,
        final long price
    ) {
        return new DeliveryOrderLineItem(
            new Random().nextLong(),
            new DeliveryOrderLineItemQuantity(quantity),
            menuId,
            BigDecimal.valueOf(price)
        );
    }

    public long getLongQuantity() {
        return quantity.getQuantity();
    }
}
