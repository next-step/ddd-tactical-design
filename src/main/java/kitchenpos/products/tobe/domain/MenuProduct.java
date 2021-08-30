package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "quantity_seq", nullable = false, foreignKey = @ForeignKey(name = "fk_menu_product_to_quantity"))
    private Quantity quantity;

    public MenuProduct() {
    }

    public MenuProduct(final UUID productId, final Quantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Price calculatePrice(final Map<UUID, Product> products) {
        Product product = products.get(productId);
        Price price = new Price(product.offerPrice());
        return price.multiply(quantity);
    }

    public boolean hasProduct(final UUID productId) {
        return this.productId.equals(productId);
    }
}
