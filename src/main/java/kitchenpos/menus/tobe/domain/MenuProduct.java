package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProduct {

    private final UUID productId;
    private final Price price;
    private final Quantity quantity;

    public MenuProduct(UUID productId, Price price, Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return price.getPrice().multiply(quantity.getQuantity());
    }

}
