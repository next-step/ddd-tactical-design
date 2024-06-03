package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "productId", columnDefinition = "binary(16)")
    @Id
    private UUID productId;

    @Transient
    private Price price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProduct() {
    }

    private MenuProduct(final UUID productId, final Price price, final int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public static final MenuProduct createMenuProduct(final UUID productId, final BigDecimal price, final int quantity) {
        return new MenuProduct(productId, Price.createPrice(price), quantity);
    }

    public BigDecimal amount() {
        return this.price.getPriceValue().multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getProductId() {
        return productId;
    }

    public void changePrice(final BigDecimal price) {
        this.price = Price.createPrice(price);
    }
}
