package kitchenpos.products.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JPAProductRepository repository;

    public ProductRepositoryImpl(JPAProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return repository.findById(productId);
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }
}
