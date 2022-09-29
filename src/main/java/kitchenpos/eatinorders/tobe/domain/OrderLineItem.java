package kitchenpos.eatinorders.tobe.domain;

import java.math.BigDecimal;
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

    @Column(name = "menu_id", nullable = false)
    private String menuId;

    @Embedded
    private Price price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    protected OrderLineItem() {
    }

    public OrderLineItem(String menuId, Price price, long quantity, boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
        this.displayed = displayed;
    }

    public BigDecimal getAmount() {
        return price.multiply(quantity);
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
