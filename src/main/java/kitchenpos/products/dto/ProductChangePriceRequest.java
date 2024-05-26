package kitchenpos.products.dto;

import java.math.BigDecimal;

public record ProductChangePriceRequest(
        BigDecimal price
) {
}
