package kitchenpos.eatinorders.tobe.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "menu_id")
    private UUID menuId;

    @Embedded
    @Column(name = "menu_price")
    private Price menuPrice;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    public OrderLineItem() {
    }

    public OrderLineItem(UUID menuId, BigDecimal menuPrice, long quantity) {
        this.seq = null;
        this.menuId = menuId;
        this.menuPrice = new Price(menuPrice);
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getMenuPrice() {
        return menuPrice.getPrice();
    }

    public long getQuantity() {
        return quantity;
    }
}
