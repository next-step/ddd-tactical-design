package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.products.domain.Product;

public class MenuProduct {
    private Product product;
    private BigDecimal price;
    private long quantity;

    public MenuProduct(Product product, long quantity) {
        validate(product, quantity);
        this.product = product;
        this.price = calcPrice(product, quantity);
        this.quantity = quantity;
    }

    private BigDecimal calcPrice(Product product, long quantity) {
        BigDecimal price = product.getPrice().getPrice();
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private void validate(Product product, long quantity) {
        if (Objects.isNull(product)) {
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
