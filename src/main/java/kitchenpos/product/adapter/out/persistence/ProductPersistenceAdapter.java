package kitchenpos.product.adapter.out.persistence;

import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.UpdateProductPort;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Product> loadAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDomainEntityMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product updateProduct(final Product product) {
        final ProductEntity entity = productRepository.save(ProductDomainEntityMapper.domainToEntity(product));
        return ProductDomainEntityMapper.entityToDomain(entity);
    }
}
