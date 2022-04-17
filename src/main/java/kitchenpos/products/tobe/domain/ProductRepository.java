package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
	Product save(Product product);

	Optional<Product> findById(UUID id);
}
