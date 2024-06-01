package kitchenpos.products.tobe.domain.vo;

import java.math.BigDecimal;

public class ProductPrice {
    private final BigDecimal price;

    private ProductPrice(BigDecimal price) {
        this.price = price;
    }

    public static ProductPrice of(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        return new ProductPrice(price);
    }
}
