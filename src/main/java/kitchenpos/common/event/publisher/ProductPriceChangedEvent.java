package kitchenpos.common.event.publisher;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPriceChangedEvent(UUID productId, BigDecimal price) {
}
