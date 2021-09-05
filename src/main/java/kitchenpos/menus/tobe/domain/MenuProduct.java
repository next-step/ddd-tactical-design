package kitchenpos.menus.tobe.domain;

import kitchenpos.common.tobe.Quantity;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuProduct {

    private final Product product;

    private final Quantity quantity;

    public MenuProduct(final Product product, final Quantity quantity) {
        validate(product);
        this.product = product;
        this.quantity = quantity;
    }

    private void validate(final Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("상품은 필수입니다");
        }
    }

    public BigDecimal getAmount() {
        final BigDecimal price = product.getPrice();
        final BigDecimal quantity = BigDecimal.valueOf(this.quantity.value());
        return price.multiply(quantity);
    }
}
