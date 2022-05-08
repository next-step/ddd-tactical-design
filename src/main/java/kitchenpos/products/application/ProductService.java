package kitchenpos.products.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Name;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        final Product product = Product.create(new Name(request.getName(), purgomalumClient), new Price(request.getPrice()));
        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(new Price(request.getPrice()));
		// TODO : 가격변경으로 인한 menu의 노출 / 비노출 여부는 finally consistence만 유지
        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
		return productRepository.findAll().stream()
			.map(ProductResponse::from)
			.collect(Collectors.toList());
    }
}
