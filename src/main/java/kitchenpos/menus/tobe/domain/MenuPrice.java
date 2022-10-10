package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private static final String EMPTY_NAME_MESSAGE = "메뉴 가격은 비어있을 수 없습니다.";
    private static final String INVALID_PRICE_MESSAGE = "메뉴 가격은 0보다 크거다 같아야 합니다.";

    private final BigDecimal price;

    public MenuPrice(final BigDecimal price) {
        if (null == price) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        final MenuPrice menuPrice = (MenuPrice) o;
        return price == null ? menuPrice.price == null : price.compareTo(menuPrice.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
