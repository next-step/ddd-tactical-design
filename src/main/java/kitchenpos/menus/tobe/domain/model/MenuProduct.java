package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.Quantity;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProduct {

    public MenuProduct(UUID productId, Price price, Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    private final UUID productId;
    private final Price price;
    private final Quantity quantity;

    public UUID getProductId() {
        return this.productId;
    }

    public BigDecimal getAmount() {
        return price.getPrice().multiply(quantity.getQuantity());
    }

}
