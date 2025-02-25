package kitchenpos.menu.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProduct() {}

    public MenuProduct(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
