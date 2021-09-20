package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.domain.Profanities;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import kitchenpos.products.tobe.ui.ProductPriceChangeRequest;

@Service(value = "TobeProductService")
public class ProductService {
	private final ProductRepository productRepository;
	private final Profanities profanities;
	private final MenuRepository menuRepository;

	public ProductService(
		ProductRepository productRepository,
		Profanities profanities,
		MenuRepository menuRepository
	) {
		this.productRepository = productRepository;
		this.profanities = profanities;
		this.menuRepository = menuRepository;
	}

	@Transactional
	public Product create(ProductCreateRequest request) {
		DisplayedName name = new DisplayedName(request.getName(), profanities);
		Price price = new Price(new BigDecimal(request.getPrice()));
		return productRepository.save(new Product(name, price));
	}

	@Transactional
	public Product changePrice(UUID id, ProductPriceChangeRequest request) {
		Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
		Price price = new Price(new BigDecimal(request.getPrice()));
		product.changePrice(price);
		List<Menu> menus = menuRepository.findAllByProductId(product.getId());
		menus.forEach(Menu::updateDisplayed);
		return product;
	}

	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}
}
