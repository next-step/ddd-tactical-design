package kitchenpos.products.domain.vo;

import kitchenpos.products.domain.exception.InvalidProductPriceException;

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

    public ProductPrice changePrice(BigDecimal price) {
        return new ProductPrice(price);
    }
}
