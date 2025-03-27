package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "menu_product")
@Entity(name = "tobeMenuProduct")
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private MenuProductQuantity quantity;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, MenuProductQuantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public MenuProductQuantity getQuantity() {
        return quantity;
    }
}
