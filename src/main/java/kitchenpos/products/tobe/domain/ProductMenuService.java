package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.event.ProductPriceChanged;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductMenuService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductMenuService(ProductRepository productRepository, ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void changeProductPrice(UUID productId, BigDecimal price) {
        Product product = productRepository.findById(productId)
            .orElseThrow(
                () -> new IllegalArgumentException("가격을 변경할 Product가 존재하지 않습니다. " + productId)
            );

        product.changePrice(price);

        eventPublisher.publishEvent(new ProductPriceChanged(productId, price));
    }
}
