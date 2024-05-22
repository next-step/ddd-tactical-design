package kitchenpos.products.dto;

import java.math.BigDecimal;

public record ProductCreateRequest(
        String name,
        BigDecimal price
) {
}
