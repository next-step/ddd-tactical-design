package kitchenpos.product.adapter.in;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;

import java.util.UUID;

class ProductMapper {
    private ProductMapper() {}

    static Product requestToDomain(final CreateProductRequest request) {
        return Product.of(ProductId.newId(), request.getName(), request.getPrice());
    }

    static Product requestToDomain(final UUID uuid, final CreateProductRequest request) {
        return Product.of(ProductId.from(uuid), request.getName(), request.getPrice());
    }

    static ProductResponse domainToResponse(final Product product) {
        return new ProductResponse(product.getId().getValue(), product.getName(), product.getPrice());
    }
}
