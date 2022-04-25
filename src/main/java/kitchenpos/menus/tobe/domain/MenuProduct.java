package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MenuProduct {
    private UUID productId;
    private BigDecimal price;
    private long quantity;

    public MenuProduct(UUID productId, BigDecimal price, long quantity) {
        validatePrice(productId, quantity);
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    private void validatePrice(UUID productId, long quantity) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException();
        }
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }
}
