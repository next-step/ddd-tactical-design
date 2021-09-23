package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity(name = "TobeOrderLineItem")
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(
            name = "menu_id",
            columnDefinition = "varbinary(16)",
            nullable = false
    )
    private UUID menuId;

    @Embedded
    private OrderQuantity orderQuantity;

    @Transient
    private BigDecimal price;

    protected OrderLineItem() {}

    public OrderLineItem(final UUID menuId, final OrderQuantity orderQuantity, final BigDecimal price) {
        this.menuId = menuId;
        this.orderQuantity = orderQuantity;
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return orderQuantity.getQuantity();
    }

    public BigDecimal getPrice() {
        return price;
    }
}
