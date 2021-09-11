package kitchenpos.menus.tobe.domain;

import kitchenpos.commons.tobe.domain.Price;
import kitchenpos.commons.tobe.domain.Quantity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MenuProduct {

    private final UUID id;

    private final UUID productId;

    private final Price price;

    private final Quantity quantity;

    public MenuProduct(final UUID id, final UUID productId, final Price price, final Quantity quantity) {
        validate(productId);

        this.id = id;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    private void validate(final UUID productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("상품 식별자는 필수입니다");
        }
    }

    public BigDecimal getAmount() {
        final BigDecimal price = this.price.value();
        final BigDecimal quantity = BigDecimal.valueOf(this.quantity.value());
        return price.multiply(quantity);
    }
}
