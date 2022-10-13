package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;

public class MenuProduct {
    private static final String LESS_THAN_ZERO_MESSAGE = "수량은 0보다 작을 수 없습니다.";

    private long quantity;
    private DisplayedName name;
    private Price price;

    public MenuProduct(final long quantity, final DisplayedName name, final Price price) {
        if (quantity < 0) {
            new IllegalArgumentException(LESS_THAN_ZERO_MESSAGE);
        }
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public MenuProduct(final long quantity, final DisplayedName name, final BigDecimal price) {
        this(quantity, name, new Price(price));
    }

    public Price getSumOfPrice() {
        return price.multiply(quantity);
    }
}
