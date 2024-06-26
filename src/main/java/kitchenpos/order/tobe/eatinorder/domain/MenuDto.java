package kitchenpos.order.tobe.eatinorder.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuDto(UUID id, boolean isDisplayed, BigDecimal price) {
    public static MenuDto of(UUID id, boolean isDisplayed, BigDecimal price) {
        return new MenuDto(id, isDisplayed, price);
    }
}
