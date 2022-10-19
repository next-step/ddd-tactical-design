package kitchenpos.product.tobe.infra.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.product.tobe.domain.entity.Product;
import kitchenpos.product.tobe.domain.repository.ProductRepository;
import kitchenpos.product.tobe.infra.jpa.ProductEntityConverter.ProductEntityToProductConverter;
import kitchenpos.product.tobe.infra.jpa.ProductEntityConverter.ProductToProductEntityConverter;
import org.springframework.stereotype.Repository;

@Repository
public class JpaProductRepository implements ProductRepository {

    private final JpaProductDao jpaProductDao;

    private final ProductToProductEntityConverter productToProductEntityConverter;

    private final ProductEntityToProductConverter productEntityToProductConverter;

    public JpaProductRepository(
        JpaProductDao jpaProductDao,
        ProductToProductEntityConverter productToProductEntityConverter,
        ProductEntityToProductConverter productEntityToProductConverter
    ) {
        this.jpaProductDao = jpaProductDao;
        this.productToProductEntityConverter = productToProductEntityConverter;
        this.productEntityToProductConverter = productEntityToProductConverter;
    }

    @Override
    public Product save(Product product) {
        final ProductEntity productEntity = this.productToProductEntityConverter.convert(product);
        final ProductEntity result = this.jpaProductDao.save(productEntity);
        return this.productEntityToProductConverter.convert(result);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        final Optional<ProductEntity> result = this.jpaProductDao.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.productEntityToProductConverter.convert(result.get()));
    }

    @Override
    public List<Product> findAll() {
        return this.jpaProductDao.findAll()
            .stream()
            .map(this.productEntityToProductConverter::convert)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return this.jpaProductDao.findAllByIdIn(ids)
            .stream()
            .map(this.productEntityToProductConverter::convert)
            .collect(Collectors.toUnmodifiableList());
    }
}
