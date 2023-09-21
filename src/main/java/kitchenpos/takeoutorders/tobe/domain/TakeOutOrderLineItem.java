package kitchenpos.takeoutorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Table(name = "take_out_order_line_item")
@Entity
public class TakeOutOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID menuId;

    @Transient
    private BigDecimal price;

    protected TakeOutOrderLineItem() {
    }

    public TakeOutOrderLineItem(final Long seq, final long quantity, final UUID menuId, final BigDecimal price) {
        this.seq = seq;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public static TakeOutOrderLineItem create(final long quantity, final UUID menuId, final long price) {
        return new TakeOutOrderLineItem(
            new Random().nextLong(),
            quantity,
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
}
