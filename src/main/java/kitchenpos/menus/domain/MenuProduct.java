package kitchenpos.menus.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuProduct {
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private Quantity quantity;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, int quantity, Long productPrice) {
        this(productId, new Quantity(quantity), new Price(productPrice));
    }

    public MenuProduct(UUID productId, Quantity quantity, Price productPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = productPrice.multiplyQuantity(quantity);
    }

    public void changeProductPrice(Price productPrice) {
        this.price = productPrice.multiplyQuantity(quantity);
    }

    public UUID getProductId() {
        return productId;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
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
