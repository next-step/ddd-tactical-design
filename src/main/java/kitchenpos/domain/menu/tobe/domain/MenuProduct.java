package kitchenpos.domain.menu.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Embedded
    private MenuProductPrice price;

    @Embedded
    private MenuProductQuantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, BigDecimal price, long quantity) {
        this.productId = productId;
        this.price = new MenuProductPrice(price);
        this.quantity = new MenuProductQuantity(quantity);
    }

    public UUID productId() {
        return productId;
    }

    public BigDecimal totalPrice() {
        return price().multiply(BigDecimal.valueOf(quantity()));
    }

    public BigDecimal price() {
        return price.getPrice();
    }

    public long quantity() {
        return quantity.getQuantity();
    }
}
