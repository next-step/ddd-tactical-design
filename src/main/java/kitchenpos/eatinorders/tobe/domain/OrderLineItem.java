package kitchenpos.eatinorders.tobe.domain;


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

    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private BigDecimal price;

    protected OrderLineItem() {
    }

    public static OrderLineItem of(UUID menuId, long quantity, BigDecimal price) {
        return new OrderLineItem(menuId, quantity, price);
    }

    public OrderLineItem(UUID menuId, long quantity, BigDecimal price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
