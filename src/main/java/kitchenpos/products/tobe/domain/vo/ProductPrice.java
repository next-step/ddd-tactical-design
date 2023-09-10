package kitchenpos.products.tobe.domain.vo;

import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;

import java.math.BigDecimal;

public class ProductPrice {
    private final BigDecimal price;

    public ProductPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductPriceException();
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
