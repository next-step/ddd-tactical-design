package kitchenpos.products.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProductPriceChangeEvent;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import kitchenpos.common.domain.ProfanityValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProfanityValidator profanityValidator;
    private final ApplicationEventPublisher publisher;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final ProfanityValidator profanityValidator,
        ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanityValidator = profanityValidator;
        this.publisher = publisher;
    }

    @Transactional
    public Product create(final ProductRequest request) {
        final Long price = request.getPrice();
        final String name = request.getName();
        final Product product = Product.from(name, price, profanityValidator);

        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductRequest request) {
        final Long price = request.getPrice();

        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changeProductPrice(price);
        publisher.publishEvent(new ProductPriceChangeEvent(productId, price));
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
