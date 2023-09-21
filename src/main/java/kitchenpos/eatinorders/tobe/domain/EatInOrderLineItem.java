package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {
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

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(final Long seq, final long quantity, final UUID menuId, final BigDecimal price) {
        this.seq = seq;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public static EatInOrderLineItem create(final long quantity, final UUID menuId, final long price) {
        return new EatInOrderLineItem(
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
