package kitchenpos.menu.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuProductQty;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Embedded
    private MenuProductQty quantity;

    protected MenuProduct() {}

    public MenuProduct(UUID productId, MenuProductQty quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProductQty getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
