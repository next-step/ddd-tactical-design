package kitchenpos.products.tobe.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal price
) {
    public static ProductResponse of(UUID id, String name, BigDecimal price) {
        return new ProductResponse(id, name, price);
    }
}
