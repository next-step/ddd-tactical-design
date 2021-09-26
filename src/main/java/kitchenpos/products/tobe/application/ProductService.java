package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPriceChangedEvent;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.domain.Profanities;
import kitchenpos.products.tobe.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.tobe.ui.dto.ProductCreateRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final Profanities profanities;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final Profanities profanities, final ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanities = profanities;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        final Product product = request.toEntity(profanities);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(UUID productId, ProductChangePriceRequest request) {
        final Product product = findById(productId);
        product.changePrice(request.price());
        eventPublisher.publishEvent(new ProductPriceChangedEvent(productId));
        return product;
    }


    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(final UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }
}
