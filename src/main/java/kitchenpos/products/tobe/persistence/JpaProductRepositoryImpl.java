package kitchenpos.products.tobe.persistence;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    public JpaProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = new ProductEntity(product);
        return jpaProductRepository.save(productEntity).toDomain();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        Optional<ProductEntity> productEntity = jpaProductRepository.findById(id);
        return productEntity.map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return jpaProductRepository.findAllById(ids)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }
}
