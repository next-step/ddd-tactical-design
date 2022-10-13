package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;

class Product {
    private Price price;

    public Product(final Price price) {
        this.price = price;
    }

    public Product(final BigDecimal price) {
        this(new Price(price));
    }

    public Price getPrice() {
        return price;
    }
}
