package kitchenpos.menus.tobe.domain;

import kitchenpos.products.domain.Product;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "menu_product_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private MenuProductQuantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @Transient
    private UUID productId;

    protected MenuProduct() { }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity.value();
    }

    public UUID getProductId() {
        return product.getId();
    }
}
