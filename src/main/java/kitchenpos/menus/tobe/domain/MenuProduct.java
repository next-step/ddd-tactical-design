package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    private UUID productId;

    public MenuProduct() {
    }

    public MenuProduct(UUID productId, long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
        this.productId = productId;
        this.quantity = quantity;
    }


    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }
}
