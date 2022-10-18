package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu_product")
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Embedded
    private Price price;

    @Embedded
    private Quantity quantity;

    protected MenuProduct() {

    }

    public MenuProduct(UUID productId, Price price, Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Price amount() {
        return price.times(quantity.quantity());
    }

    public boolean satisfyProduct(UUID productId) {
        return Objects.equals(this.productId, productId);
    }

}
