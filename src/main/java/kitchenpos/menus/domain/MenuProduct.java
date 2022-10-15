package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.MenuProductQuantity;
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
    @Column(name = "quantity", nullable = false)
    private MenuProductQuantity quantity;

    @Transient
    private UUID productId;

    public MenuProduct() {
    }

    public MenuProduct(Long seq, Product product, MenuProductQuantity quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
    }

    public MenuProduct(Product product, MenuProductQuantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long seq() {
        return seq;
    }

    public Product product() {
        return product;
    }

    public MenuProductQuantity quantity() {
        return quantity;
    }

    public long quantityValue() {
        return quantity.quantity();
    }

    public UUID productId() {
        return productId;
    }
}
