package kitchenpos.core.products.application;

import kitchenpos.core.products.tobe.domain.Product;
import kitchenpos.core.products.tobe.domain.ProductPrice;
import kitchenpos.core.products.tobe.domain.exception.ProductNotFoundException;
import kitchenpos.core.shared.identifier.ProductId;

public interface ChangeProductPrice {
    Product changePrice(final ProductId productId, final ProductPrice request) throws ProductNotFoundException;
}
