package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.ProductPricePolicy;
import kitchenpos.products.tobe.domain.DisplayedNamePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.application.dto.ProductChangePriceRequest;
import kitchenpos.products.application.dto.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPricePolicy productPricePolicy;
    private final DisplayedNamePolicy displayedNamePolicy;

    public ProductService(
            ProductRepository productRepository,
            ProductPricePolicy productPricePolicy,
            DisplayedNamePolicy displayedNamePolicy
    ) {
        this.productRepository = productRepository;
        this.productPricePolicy = productPricePolicy;
        this.displayedNamePolicy = displayedNamePolicy;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        Product product = request.toProduct(displayedNamePolicy);
        Product productWithId = product.giveId();
        return productRepository.save(productWithId);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final ProductPrice changePrice = ProductPrice.from(request.getPrice());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        productPricePolicy.changePrice(product, changePrice);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
