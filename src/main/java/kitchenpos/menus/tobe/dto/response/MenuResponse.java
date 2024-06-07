package kitchenpos.menus.tobe.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuResponse(
        UUID id,
        UUID menuGroupId,
        String name,
        BigDecimal price
) {
}
