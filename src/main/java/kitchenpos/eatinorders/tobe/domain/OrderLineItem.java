package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "order_line_item")
public class OrderLineItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menuId;

    private BigDecimal price;

    private int quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(Long menuId, BigDecimal price, int quantity) {
        this(null, menuId, price, quantity);
    }

    public OrderLineItem(Long id, Long menuId, BigDecimal price, int quantity) {
        this.id = id;
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equalsId(Long id) {
        return this.id.equals(id);
    }

    public Long getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
