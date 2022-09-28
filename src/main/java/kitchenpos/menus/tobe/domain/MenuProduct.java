package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Embedded
    private Price price;

    @Embedded
    private Quantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(String productId, Price price, Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return price.multiply(quantity);
    }

    protected void changePrice(Price price) {
        this.price = price;
    }
}
