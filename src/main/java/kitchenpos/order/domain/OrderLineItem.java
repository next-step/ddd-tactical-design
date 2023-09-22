package kitchenpos.order.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;
    private UUID menuId;
    @Column(name = "quantity", nullable = false)
    private long quantity;
    @Transient
    private BigDecimal price;

    public OrderLineItem() {
    }

    public OrderLineItem(UUID menuId, long quantity, BigDecimal price) {
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }


    public long getQuantity() {
        return quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal multiply() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }
}
