package kitchenpos.product.adapter.out.persistence;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;

class ProductDomainEntityMapper {
    private ProductDomainEntityMapper() {}

    static Product entityToDomain(final ProductEntity entity) {
        return Product.of(ProductId.from(entity.getId()), entity.getName(), entity.getPrice());
    }

    static ProductEntity domainToEntity(final Product domain) {
        final ProductEntity entity = new ProductEntity();

        entity.setId(domain.getId().getValue());
        entity.setName(domain.getName());
        entity.setPrice(domain.getPrice());

        return entity;
    }
}
