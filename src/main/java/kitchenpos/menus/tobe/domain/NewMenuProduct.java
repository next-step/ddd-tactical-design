package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.ILLEGAL_QUANTITY;

@Table(name = "menu_product")
@Entity
public class NewMenuProduct {
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

    public NewMenuProduct() {
    }

    private NewMenuProduct(UUID productId, long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(ILLEGAL_QUANTITY);
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    private NewMenuProduct(Long seq, UUID productId, long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(ILLEGAL_QUANTITY);
        }
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static NewMenuProduct create(UUID productId, long quantity) {
        return new NewMenuProduct(productId, quantity);
    }

    public static NewMenuProduct create(Long seq, UUID productId, long quantity) {
        return new NewMenuProduct(seq, productId, quantity);
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewMenuProduct that = (NewMenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
