package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.menu.domain.exception.MenuPriceException;

@Embeddable
public record MenuPrice(BigDecimal price) {

    public static MenuPrice of(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new MenuPriceException();
        }
        return new MenuPrice(price);
    }

    public boolean isEqual(BigDecimal diff) {
        return price.compareTo(diff) == 0;
    }

    public boolean isLessThanOrEqual(BigDecimal diff) {
        return price.compareTo(diff) <= 0;
    }

    public boolean isGreaterThan(BigDecimal diff) {
        return price.compareTo(diff) > 0;
    }

    public BigDecimal get() {
        return price;
    }
}
