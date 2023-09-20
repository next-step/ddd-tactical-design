package kitchenpos.deliveryorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    @Column(name = "quantity", nullable = false)
    private OrderLineItemQuantity quantity;

    @Transient
    private UUID menuId;

    @Transient
    private BigDecimal price;

    protected OrderLineItem() {
    }

    public OrderLineItem(
        final Long seq,
        final OrderLineItemQuantity quantity,
        final UUID menuId,
        final BigDecimal price
    ) {
        this.seq = seq;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public static OrderLineItem create(
        final long quantity,
        final UUID menuId,
        final long price
    ) {
        return new OrderLineItem(
            new Random().nextLong(),
            new OrderLineItemQuantity(quantity),
            menuId,
            BigDecimal.valueOf(price)
        );
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getLongQuantity() {
        return quantity.getQuantity();
    }
}
