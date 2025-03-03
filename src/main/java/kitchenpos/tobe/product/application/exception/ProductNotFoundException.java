package kitchenpos.tobe.product.application.exception;

import kitchenpos.tobe.product.domain.ProductId;

public class ProductNotFoundException extends IllegalArgumentException {
    public ProductNotFoundException(ProductId productId) {
        super("Product not found - id : %s".formatted(productId));
    }
}
