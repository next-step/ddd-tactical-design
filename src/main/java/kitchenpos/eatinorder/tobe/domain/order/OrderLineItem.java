package kitchenpos.eatinorder.tobe.domain.order;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "menuId", nullable = false)
    private UUID menuId;

    @Transient
    private BigDecimal price;

    public static OrderLineItem of(long quantity, UUID menuId, BigDecimal price) {
        return new OrderLineItem(quantity, menuId, price);
    }

    private OrderLineItem(long quantity, UUID menuId, BigDecimal price) {
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    protected OrderLineItem() {
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
}
