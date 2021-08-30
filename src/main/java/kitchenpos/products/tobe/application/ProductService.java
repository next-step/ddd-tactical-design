package kitchenpos.products.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import kitchenpos.products.tobe.ui.ProductPriceChangeRequest;

public class ProductService {
	private final ProductRepository productRepository;
	private final PurgomalumClient purgomalumClient;

	public ProductService(
		ProductRepository productRepository,
		PurgomalumClient purgomalumClient
	) {
		this.productRepository = productRepository;
		this.purgomalumClient = purgomalumClient;
	}

	public Product create(ProductCreateRequest request) {
		DisplayedName name = new DisplayedName(request.getName(), purgomalumClient);
		Price price = new Price(request.getPrice());
		return productRepository.save(new Product(name, price));
	}

	public Product changePrice(UUID id, ProductPriceChangeRequest request) {
		Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
		Price price = new Price(request.getPrice());
		product.changePrice(price);
		return product;
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}
}
