package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_PRICE_MORE_ZERO;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private Integer price;

    protected Price() {}

    private Price(Integer price) {
        if (price < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_MORE_ZERO);
        }
        this.price = price;
    }

    public static Price of(Integer price) {
        return new Price(price);
    }

    public Integer getPrice() {
        return price;
    }
}
