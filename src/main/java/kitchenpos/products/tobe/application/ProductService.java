package kitchenpos.products.tobe.application;

import static kitchenpos.products.tobe.domain.ProductName.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.tobe.domain.PurgomalumClient;
import kitchenpos.products.tobe.application.dto.ProductCreationRequest;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final MenuService menuService;
	private final PurgomalumClient purgomalumClient;

	public ProductService(
		final ProductRepository productRepository,
		final MenuService menuService,
		final PurgomalumClient purgomalumClient
	) {
		this.productRepository = productRepository;
		this.menuService = menuService;
		this.purgomalumClient = purgomalumClient;
	}

	@Transactional
	public Product create(final ProductCreationRequest request) {
		validateProductName(request.name());

		final Product product = new Product(
			UUID.randomUUID(),
			request.name(),
			request.price()
		);

		return productRepository.save(product);
	}

	private void validateProductName(String name) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException(NULL_OR_EMPTY_NAME_ERROR);
		}

		if (purgomalumClient.containsProfanity(name)) {
			throw new IllegalArgumentException(ProductName.NAME_WITH_PROFANITY_ERROR);
		}
	}

	@Transactional
	public Product changePrice(final UUID productId, final BigDecimal price) {
		final Product product = productRepository.findById(productId)
			.orElseThrow(NoSuchElementException::new);

		product.changePrice(price);
		menuService.hideMenusBasedOnProductPrice(productId);

		return product;
	}

	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Product> findAllByIdIn(List<UUID> ids) {
		return productRepository.findAllByIdIn(ids);
	}
}
