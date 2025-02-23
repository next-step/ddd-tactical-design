package kitchenpos.menu.domain.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.global.exception.ErrorCode;

@Embeddable
public record MenuPrice(BigDecimal price) {

    public static MenuPrice of(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ErrorCode.MENU_PRICE_NOT_ALLOWED.toString());
        }
        return new MenuPrice(price);
    }
}
