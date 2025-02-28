package kitchenpos.core.products.application.dto;

import kitchenpos.core.products.tobe.domain.ProductName;
import kitchenpos.core.products.tobe.domain.ProductPrice;
import kitchenpos.core.shared.identifier.ProductId;

public record CreateProductRequest(
        ProductId id,
        ProductName name,
        ProductPrice price
) {
}
