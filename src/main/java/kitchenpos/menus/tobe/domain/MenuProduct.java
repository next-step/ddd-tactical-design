package kitchenpos.menus.tobe.domain;

import kitchenpos.common.tobe.Quantity;

import java.math.BigDecimal;

public class MenuProduct {

    private final Product product;

    private  final Quantity quantity;

    public MenuProduct(final Product product, final Quantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        final BigDecimal price = product.getPrice();
        final BigDecimal quantity = BigDecimal.valueOf(this.quantity.value());
        return price.multiply(quantity);
    }
}
