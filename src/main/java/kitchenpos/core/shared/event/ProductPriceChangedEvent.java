package kitchenpos.core.shared.event;

import kitchenpos.core.products.tobe.domain.ProductPrice;
import kitchenpos.core.shared.identifier.ProductId;

public record ProductPriceChangedEvent(ProductId id, ProductPrice oldPrice, ProductPrice newPrice) {
}
