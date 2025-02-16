package kitchenpos.shared.event;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPriceChangedEvent(UUID productId, BigDecimal oldPrice,
                                       BigDecimal newPrice) implements DomainEvent {

}