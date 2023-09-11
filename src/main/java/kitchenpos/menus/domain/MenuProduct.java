package kitchenpos.menus.domain;

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

    @Column(name = "quantity", nullable = false)
    private long quantity;

    public MenuProduct() {
    }

    private MenuProduct(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    private MenuProduct(Long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static MenuProduct of(UUID productId, long quantity) {
        return new MenuProduct(productId, quantity);
    }

    public static MenuProduct of(Long seq, UUID productId, long quantity) {
        return new MenuProduct(seq, productId, quantity);
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }
}
