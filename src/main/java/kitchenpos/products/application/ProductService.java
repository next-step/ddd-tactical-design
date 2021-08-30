package kitchenpos.products.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.ui.ProductCreateRequest;
import kitchenpos.products.ui.ProductPriceChangeRequest;

@Service
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

	@Transactional
	public Product create(ProductCreateRequest request) {
		DisplayedName name = new DisplayedName(request.getName(), purgomalumClient);
		Price price = new Price(request.getPrice());
		return productRepository.save(new Product(name, price));
	}

	@Transactional
	// TODO : 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
	public Product changePrice(UUID id, ProductPriceChangeRequest request) {
		Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
		Price price = new Price(request.getPrice());
		product.changePrice(price);
		return product;
	}

	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}
}
