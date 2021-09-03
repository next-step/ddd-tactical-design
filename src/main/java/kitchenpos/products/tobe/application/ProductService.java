package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.interfaces.event.EventPublishClient;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.service.ProductDomainService;
import kitchenpos.products.tobe.ui.dto.ProductChangePriceEvent;
import kitchenpos.products.tobe.ui.dto.ProductRequests;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;
    private final EventPublishClient eventPublishClient;

    public ProductService(
            final ProductRepository productRepository,
            final ProductDomainService productDomainService,
            final EventPublishClient eventPublishClient
    ) {
        this.productRepository = productRepository;
        this.productDomainService = productDomainService;
        this.eventPublishClient = eventPublishClient;
    }

    @Transactional
    public Product create(final ProductRequests.Create request) {
        productDomainService.validateDisplayedName(request.getDisplayedName());

        return productRepository.save(new Product(request.getDisplayedName(), request.getPrice()));
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductRequests.ChangePrice request) {
        final BigDecimal price = request.getPrice();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        product.changePrice(price);
        eventPublishClient.publishEvent(new ProductChangePriceEvent(productId));

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
