package kitchenpos.product.application.port.out;

import kitchenpos.product.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadProductPort {
    Optional<Product> findById(UUID productId);
    List<Product> findAll();
    List<Product> findAllByIdIn(List<UUID> productIds);
}
