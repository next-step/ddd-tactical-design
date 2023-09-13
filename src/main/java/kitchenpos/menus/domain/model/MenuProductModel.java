package kitchenpos.menus.domain.model;


import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductModel {
    private final UUID productId;
    private final BigDecimal price;
    private final int quantity;

    public MenuProductModel(UUID productId, BigDecimal price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal amount() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
