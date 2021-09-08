package kitchenpos.products.tobe.infra.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

    @Override
    Product save(Product product);

    @Override
    Optional<Product> findById(UUID id);

    @Override
    List<Product> findAll();

    @Override
    List<Product> findAllByIdIn(List<UUID> ids);
}
