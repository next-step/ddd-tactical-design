package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Quantity quantity;

    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Embedded
    private Price price;

    public OrderLineItem() {
    }

    public OrderLineItem(UUID menuId, BigDecimal price, long quantity) {
        this.menuId = menuId;
        this.price = new Price(price);
        this.quantity = new Quantity(quantity);
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity.getValue();
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }
}
