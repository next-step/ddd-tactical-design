package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;

import javax.persistence.Column;

public class ProductPrice {
    @Column(name = "price", nullable = false)
    private final BigDecimal price;
    private final String currency;

    public ProductPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    private String defaultCurrency() {
        return "Won";
    }

    private void validationOfPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException();
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
