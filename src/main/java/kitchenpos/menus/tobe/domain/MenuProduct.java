package kitchenpos.menus.tobe.domain;

import kitchenpos.commons.tobe.domain.Price;
import kitchenpos.commons.tobe.domain.Quantity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MenuProduct {

    private final UUID menuId;

    private final UUID productId;

    private final Price price;

    private final Quantity quantity;

    public MenuProduct(final UUID menuId, final UUID productId, final Price price, final Quantity quantity) {
        validate(menuId, productId);

        this.menuId = menuId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    private void validate(final UUID menuId, final UUID productId) {
        if (Objects.isNull(menuId)) {
            throw new IllegalArgumentException("메뉴 식별자는 필수입니다");
        }
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
