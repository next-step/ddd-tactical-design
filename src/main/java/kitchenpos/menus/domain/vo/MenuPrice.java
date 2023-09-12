package kitchenpos.menus.domain.vo;

import kitchenpos.menus.domain.exception.InvalidMenuPriceException;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private final BigDecimal price;

    public MenuPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMenuPriceException();
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
