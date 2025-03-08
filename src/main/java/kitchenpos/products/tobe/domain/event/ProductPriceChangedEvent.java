package kitchenpos.products.tobe.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPriceChangedEvent(
        UUID productId, BigDecimal newPrice) {
}
