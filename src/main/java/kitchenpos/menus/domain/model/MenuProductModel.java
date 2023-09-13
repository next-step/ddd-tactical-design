package kitchenpos.menus.domain.model;


import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.support.ValueObject;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductModel extends ValueObject {
    private final UUID productId;
    private final BigDecimal price;
    private final long quantity;

    public MenuProductModel(UUID productId, BigDecimal price, long quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuProductModel(MenuProduct menuProduct) {
        this.productId = menuProduct.getProductId();
        this.price = menuProduct.getProduct().getPrice();
        this.quantity = menuProduct.getQuantity();
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal amount() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static MenuProductModel of(UUID productId, BigDecimal price, long quantity) {
        return new MenuProductModel(productId, price, quantity);
    }

    public static MenuProductModel of(MenuProduct menuProduct) {
        return new MenuProductModel(menuProduct.getProductId(), menuProduct.getProduct().getPrice(), menuProduct.getQuantity());
    }
}
