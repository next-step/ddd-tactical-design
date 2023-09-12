package kitchenpos.product.adapter.in;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;

class ProductMapper {
    private ProductMapper() {}

    static Product requestToDomain(final ProductRequest request) {
        return new Product(ProductId.from(request.getId()), request.getName(), request.getPrice());
    }

    static ProductResponse domainToResponse(final Product product) {
        return new ProductResponse(product.getId().getValue(), product.getName(), product.getPrice());
    }
}
