package kitchenpos.menus.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private UUID productId;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private Quantity quantity;

    private Price price;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, Quantity quantity, Price productPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = productPrice.multiplyQuantity(quantity);
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}
