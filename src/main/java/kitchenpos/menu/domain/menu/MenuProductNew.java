package kitchenpos.menu.domain.menu;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "menu_product_new")
@Entity
public class MenuProductNew {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private UUID id;


    @Column(name = "product_id", nullable = false)
    private UUID productId;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_price"))
    private ProductPrice productPrice;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;


    protected MenuProductNew() {
    }

    private MenuProductNew(final UUID id, final UUID productId,
        final ProductPrice productPrice, final Quantity quantity) {

        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public static MenuProductNew create(final UUID productId,
        final ProductPrice productPrice, final Quantity quantity) {

        return new MenuProductNew(UUID.randomUUID(), productId, productPrice, quantity);
    }

    void changeProductPrice(final ProductPrice productPrice) {
        checkNotNull(productPrice, "productPrice");

        this.productPrice = productPrice;
    }

    int amount() {
        return productPrice.getValue() * quantity.getValue();
    }

    public UUID getId() {
        return id;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public UUID getProductId() {
        return productId;
    }
}
