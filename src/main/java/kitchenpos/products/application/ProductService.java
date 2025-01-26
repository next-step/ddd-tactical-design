package kitchenpos.products.application;

import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

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
        final ProductName productName = new ProductName(request.getName(), purgomalumClient);
        final ProductPrice productPrice = new ProductPrice(request.getPrice());
        final Product product = productRepository.save(
                new Product(productName, productPrice)
        );
        return ProductResponse.convert(product);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final ProductPrice productPrice = new ProductPrice(request.getPrice());
        final Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        product.changePrice(productPrice);
        productRepository.save(product);
        return ProductResponse.convert(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(ProductResponse::convert).collect(Collectors.toUnmodifiableList());
    }
}
