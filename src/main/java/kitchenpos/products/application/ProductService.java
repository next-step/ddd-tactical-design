package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.MenuPricePolicy;
import kitchenpos.products.tobe.domain.DisplayedNamePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.ui.dto.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuPricePolicy menuPricePolicy;
    private final DisplayedNamePolicy displayedNamePolicy;

    public ProductService(
            ProductRepository productRepository,
            MenuPricePolicy menuPricePolicy,
            DisplayedNamePolicy displayedNamePolicy
    ) {
        this.productRepository = productRepository;
        this.menuPricePolicy = menuPricePolicy;
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
        final ProductPrice price = ProductPrice.from(request.getPrice());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        menuPricePolicy.changeMenuProductPrice(product);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
