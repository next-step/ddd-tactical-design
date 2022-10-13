package kitchenpos.menus.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "TobeMenuProduct")
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;
    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "price")
    private BigDecimal price;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, int quantity, long price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = BigDecimal.valueOf(price);
    }

    long amount() {
        return price.longValue() * quantity;
    }

    boolean matchProductId(UUID productId) {
        return this.productId.equals(productId);
    }

    void updatePrice(long price) {
        this.price = BigDecimal.valueOf(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
