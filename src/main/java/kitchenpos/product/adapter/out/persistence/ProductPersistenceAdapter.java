package kitchenpos.product.adapter.out.persistence;

import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.UpdateProductPort;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class ProductPersistenceAdapter implements LoadProductPort, UpdateProductPort {
    private final ProductRepository productRepository;

    public ProductPersistenceAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> loadProductById(final ProductId id) {
        return productRepository.findById(id.getValue())
                .map(ProductDomainEntityMapper::entityToDomain);
    }

    @Override
    public Product updateProduct(final Product product) {
        final ProductEntity entity = productRepository.save(ProductDomainEntityMapper.domainToEntity(product));
        return ProductDomainEntityMapper.entityToDomain(entity);
    }
}
