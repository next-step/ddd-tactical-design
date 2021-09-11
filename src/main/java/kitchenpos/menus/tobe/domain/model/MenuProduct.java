package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProduct {

    private final UUID id;

    private final UUID productId;

    private final Price price;

    private final Quantity quantity;

    public MenuProduct(final UUID id, final UUID productId, final Price price, final Quantity quantity) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        final BigDecimal price = this.price.value();
        final BigDecimal quantity = BigDecimal.valueOf(this.quantity.value());
        return price.multiply(quantity);
    }

    public UUID getProductId() {
        return productId;
    }
}
