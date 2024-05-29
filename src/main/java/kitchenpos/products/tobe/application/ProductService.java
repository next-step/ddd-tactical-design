package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import kitchenpos.products.tobe.application.dto.ProductCreationRequest;
import kitchenpos.products.tobe.domain.Product;

public interface ProductService {
	Product create(final ProductCreationRequest request);

	Product changePrice(final UUID productId, final BigDecimal price);

	List<Product> findAll();

	List<Product> findAllByIdIn(List<UUID> ids);
}