package kitchenpos.products.application;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import kitchenpos.products.domain.Product;

public interface ProductService {
	@Transactional
	Product create(Product request);

	@Transactional
	Product changePrice(UUID productId, Product request);

	@Transactional(readOnly = true)
	List<Product> findAll();
}
