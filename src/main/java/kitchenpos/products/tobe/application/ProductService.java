package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.Profanities;
import kitchenpos.products.tobe.ui.ProductCreateRequest;
import kitchenpos.products.tobe.ui.ProductPriceChangeRequest;

@Service(value = "TobeProductService")
public class ProductService {
	private final ProductRepository productRepository;
	private final Profanities profanities;

	public ProductService(ProductRepository productRepository, Profanities profanities) {
		this.productRepository = productRepository;
		this.profanities = profanities;
	}

	@Transactional
	public Product create(ProductCreateRequest request) {
		DisplayedName name = new DisplayedName(request.getName(), profanities);
		Price price = new Price(new BigDecimal(request.getPrice()));
		return productRepository.save(new Product(name, price));
	}

	@Transactional
	// TODO : 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
	public Product changePrice(UUID id, ProductPriceChangeRequest request) {
		Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
		Price price = new Price(new BigDecimal(request.getPrice()));
		product.changePrice(price);
		return product;
	}

	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}
}
