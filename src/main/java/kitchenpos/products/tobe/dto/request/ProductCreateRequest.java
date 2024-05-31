package kitchenpos.products.tobe.dto.request;

import java.math.BigDecimal;

public record ProductCreateRequest(
        String name,
        BigDecimal price
) {
}
