package kitchenpos.product.adapter.out.persistance;

import kitchenpos.product.adapter.out.persistance.entity.ProductEntity;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.SaveProductPort;
import kitchenpos.product.application.service.validator.DefaultProfanityFilteringProductNameValidator;
import kitchenpos.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ManageProductAdapter implements SaveProductPort, LoadProductPort {
    private final ProductEntityRepository jpaProductRepository;
    private final DefaultProfanityFilteringProductNameValidator profanityFilteringProductNameValidator;

    public ManageProductAdapter(
            JpaProductEntityRepository jpaProductRepository,
            DefaultProfanityFilteringProductNameValidator profanityFilteringProductNameValidator
    ) {
        this.jpaProductRepository = jpaProductRepository;
        this.profanityFilteringProductNameValidator = profanityFilteringProductNameValidator;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return jpaProductRepository.findById(productId)
                .map(productEntity -> productEntity.toDomain(profanityFilteringProductNameValidator));
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
                .stream()
                .map(productEntity -> productEntity.toDomain(profanityFilteringProductNameValidator))
                .toList();
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        return jpaProductRepository.findAllByIdIn(productIds)
                .stream()
                .map(productEntity -> productEntity.toDomain(profanityFilteringProductNameValidator))
                .toList();
    }

    @Override
    public Product save(Product product) {
        return jpaProductRepository.save(ProductEntity.of(product))
                .toDomain(profanityFilteringProductNameValidator);
    }
}
