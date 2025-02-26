package kitchenpos.global.event;

import kitchenpos.product.domain.model.ProductId;

public sealed interface ProductEvent permits
    ProductEvent.ProductPriceChangedEvent
{

    ProductId productId();

    record ProductPriceChangedEvent(ProductId productId) implements ProductEvent {}
}
