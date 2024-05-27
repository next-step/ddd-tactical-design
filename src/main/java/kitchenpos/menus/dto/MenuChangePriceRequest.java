package kitchenpos.menus.dto;

import java.math.BigDecimal;

public record MenuChangePriceRequest(
        BigDecimal price
) {
}
