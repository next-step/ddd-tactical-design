package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.product.domain.Product;

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

    protected MenuProduct() {}

    public MenuProduct(Long seq, Product product, long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
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
