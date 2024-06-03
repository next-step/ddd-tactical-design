package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
public class MenuProduct {

    private UUID productId;

    private Price price;

    private int quantity;

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
