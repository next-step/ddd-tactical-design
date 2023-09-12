package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.util.Random;
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
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID productId;

    public MenuProduct() {
    }

    public MenuProduct(final UUID productId, final long quantity) {
        this(new Random().nextLong(), null, quantity, productId);
    }

    public MenuProduct(final Product product, final long quantity) {
        this(new Random().nextLong(), product, quantity);
    }

    public MenuProduct(final Long seq, final Product product, final long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
    }

    public MenuProduct(final Long seq, final Product product, long quantity, final UUID productId) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
