package kitchenpos.menus.domain;


import kitchenpos.menus.domain.vo.MenuProductQuantity;
import kitchenpos.products.domain.Product;

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

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID productId;

    public MenuProduct(Long seq, Product product, long quantity, UUID productId) {
        this.seq = seq;
        this.product = product;
        this.quantity = new MenuProductQuantity(quantity).getQuantity();
        this.productId = productId;
    }

    public MenuProduct(Product product, long quantity, UUID productId) {
        this.product = product;
        this.quantity = new MenuProductQuantity(quantity).getQuantity();
        this.productId = productId;
    }

    public MenuProduct(UUID productId, long quantity) {
        this.quantity = new MenuProductQuantity(quantity).getQuantity();
        this.productId = productId;
    }

    public MenuProduct() {

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
