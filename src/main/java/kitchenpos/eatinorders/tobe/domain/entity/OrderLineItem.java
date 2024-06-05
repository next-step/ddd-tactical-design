package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item2")
@Entity(name = "OrderLineItem2")
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private BigDecimal price;

    @Transient
    private UUID menuId;

    protected OrderLineItem() {}

    public OrderLineItem(Long seq, long quantity, BigDecimal price) {
        this(seq, quantity, price, null);
    }

    public OrderLineItem(Long seq, long quantity, BigDecimal price, UUID menuId) {
        this.seq = seq;
        this.quantity = quantity;
        this.price = price;
        this.menuId = menuId;
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuId() {
        return menuId;
    }
}
