package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {
    private static final int MIN_PRICE = 0;
    private static final String INVALID_PRICE_MESSAGE = "상품 가격은 0보다 크거다 같아야 합니다.";
    private final BigDecimal price;
    private final String name;

    public Product(final BigDecimal price, final String name) {
        if (price.compareTo(BigDecimal.ZERO) < MIN_PRICE) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
        this.price = price;
        this.name = name;
    }
}
