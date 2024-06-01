package kitchenpos.menus.domain.tobe;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProduct {
    private final UUID productId;
    private Price price;
    private final int quantity;

    private MenuProduct(Price price, int quantity) {
        this.productId = UUID.randomUUID();
        this.price = price;
        this.quantity = quantity;
    }

    public static final MenuProduct createMenuProduct(BigDecimal price, int quantity){
        return new MenuProduct(Price.createPrice(price), quantity);
    }

    public BigDecimal amount() {
        return this.price.getPriceValue().multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getProductId() {
        return productId;
    }

    public void changePrice(final Price price) {
        this.price = price;
    }
}
