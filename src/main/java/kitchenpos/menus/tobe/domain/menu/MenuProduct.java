package kitchenpos.menus.tobe.domain.menu;

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

    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private UUID productId;
    @Embedded
    private MenuProductQuantity quantity;

    public MenuProduct(UUID productId, MenuProductQuantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    protected MenuProduct() {
    }

    public static MenuProduct of(UUID productId, MenuProductQuantity quantity) {
        return new MenuProduct(productId, quantity);
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity.getMenuProductQuantity();
    }

    public UUID getProductId() {
        return productId;
    }
}
