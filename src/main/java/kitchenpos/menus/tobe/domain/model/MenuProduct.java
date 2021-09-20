package kitchenpos.menus.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import kitchenpos.common.tobe.domain.Price;

@Table(name = "menu_product")
@Entity(name = "tobeMenuProduct")
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Quantity quantity;

    @Column(
        name = "product_id",
        nullable = false,
        columnDefinition = "varbinary(16)"
    )
    private UUID productId;

    @Transient
    private Price productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(final Quantity quantity, final UUID productId, final Price productPrice) {
        this.quantity = quantity;
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public Price getMenuProductPrice() {
        return productPrice.multiply(quantity);
    }

    public UUID getProductId() {
        return productId;
    }
}
