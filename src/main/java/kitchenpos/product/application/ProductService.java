package kitchenpos.product.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.application.dto.ProductCreationRequest;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final MenuRepository menuRepository;
	private final PurgomalumClient purgomalumClient;

	public ProductService(
		final ProductRepository productRepository,
		final MenuRepository menuRepository,
		final PurgomalumClient purgomalumClient
	) {
		this.productRepository = productRepository;
		this.menuRepository = menuRepository;
		this.purgomalumClient = purgomalumClient;
	}

	@Transactional
	public Product create(final ProductCreationRequest request) {
		final Product product = new Product(
			UUID.randomUUID(),
			request.name(),
			request.price(),
			purgomalumClient
		);

		return productRepository.save(product);
	}

	@Transactional
	public Product changePrice(final UUID productId, final BigDecimal price) {
		final Product product = productRepository.findById(productId)
			.orElseThrow(NoSuchElementException::new);

		product.changePrice(price);

		final List<Menu> menus = menuRepository.findAllByProductId(productId);

		if (!CollectionUtils.isEmpty(menus)) {
			menus.forEach(Menu::hideBasedOnProductsPrice);
		}

		return product;
	}

	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}
}
