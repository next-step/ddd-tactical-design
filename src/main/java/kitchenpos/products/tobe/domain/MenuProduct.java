package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "quantity_seq", nullable = false, foreignKey = @ForeignKey(name = "fk_menu_product_to_quantity"))
    private Quantity quantity;

    public MenuProduct(final Product product, final Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public MenuProduct() {
    }

    public Product getProduct() {
        return product;
    }

    public boolean hasProduct(final UUID productId) {
        return product.equals(productId);
    }
}
