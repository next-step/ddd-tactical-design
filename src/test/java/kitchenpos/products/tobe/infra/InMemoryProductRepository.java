package kitchenpos.products.tobe.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.global.helper.InMemoryRepository;
import kitchenpos.products.tobe.domain.model.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class InMemoryProductRepository implements ProductRepository {

    private final InMemoryRepository<UUID, Product> repository = new InMemoryRepository<>();

    @Override
    public Product save(Product product) {
        return repository.save(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Product getById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return findAll().stream()
            .filter(product -> ids.contains(product.getId()))
            .collect(Collectors.toUnmodifiableList());
    }
}
