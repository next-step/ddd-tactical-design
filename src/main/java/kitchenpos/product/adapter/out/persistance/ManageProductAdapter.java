package kitchenpos.product.adapter.out.persistance;

import kitchenpos.product.adapter.out.persistance.entity.ProductEntity;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.SaveProductPort;
import kitchenpos.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ManageProductAdapter implements SaveProductPort, LoadProductPort {
    private final ProductEntityRepository jpaProductRepository;

    public ManageProductAdapter(JpaProductEntityRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return jpaProductRepository.findById(productId)
                .map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        return jpaProductRepository.findAllByIdIn(productIds)
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public Product save(Product product) {
        return jpaProductRepository.save(ProductEntity.of(product))
                .toDomain();
    }
}
