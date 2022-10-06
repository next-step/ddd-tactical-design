package kitchenpos.menus.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    private UUID productId;
    private int quantity;
    private long price;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, int quantity, long price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    long amount() {
        return price * quantity;
    }

    boolean matchProductId(UUID productId) {
        return this.productId.equals(productId);
    }

    void updatePrice(long price) {
        this.price = price;
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
