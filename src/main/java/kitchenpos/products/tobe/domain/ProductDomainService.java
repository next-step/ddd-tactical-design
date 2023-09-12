package kitchenpos.products.tobe.domain;

import kitchenpos.products.event.ProductPriceChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductDomainService {
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductDomainService(ProductRepository productRepository, ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void changePrice(UUID productId, BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new IllegalArgumentException("상품이 존재하지 않습니다 : " + productId)
                );

        product.changePrice(price);

        eventPublisher.publishEvent(new ProductPriceChangedEvent(productId, price));

    }

}