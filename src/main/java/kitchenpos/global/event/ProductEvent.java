package kitchenpos.global.event;

import java.util.UUID;

public sealed interface ProductEvent permits
    ProductEvent.ProductPriceChangedEvent
{

    UUID productId();

    record ProductPriceChangedEvent(UUID productId) implements ProductEvent {}
}
