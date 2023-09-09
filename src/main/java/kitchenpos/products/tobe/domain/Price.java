package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.math.BigDecimal;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_PRICE_MORE_ZERO;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {}

    private Price(BigDecimal price) {
        if (price == null || price.intValue() < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_MORE_ZERO);
        }
        this.price = price;
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal getPrice() {
        return price;
    }
}
