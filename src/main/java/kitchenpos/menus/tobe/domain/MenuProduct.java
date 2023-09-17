package kitchenpos.menus.tobe.domain;


import kitchenpos.products.tobe.domain.Product;

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
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Embedded
    private MenuProductQuantity quantity;

    @Transient
    private UUID productId;

    protected MenuProduct() {
    }

    protected MenuProduct(Product product, MenuProductQuantity quantity, UUID productId) {
        this.product = product;
        this.quantity = quantity;
        this.productId = productId;
    }

    public static MenuProduct create(Product product, MenuProductQuantity quantity) {
        return new MenuProduct(product, quantity, product.getId());
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity.getValue();
    }

    public UUID getProductId() {
        return productId;
    }
}
