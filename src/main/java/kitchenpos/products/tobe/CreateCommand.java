package kitchenpos.products.tobe;

import java.math.BigDecimal;

public record CreateCommand(
        String name,
        BigDecimal price
) {
}
