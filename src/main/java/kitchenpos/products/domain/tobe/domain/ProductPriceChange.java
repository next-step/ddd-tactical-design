package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;

import kitchenpos.common.DomainService;

@DomainService
public class ProductPriceChange {
    private final ToBeProductRepository toBeProductRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductPriceChange(ToBeProductRepository toBeProductRepository,
        ApplicationEventPublisher applicationEventPublisher) {
        this.toBeProductRepository = toBeProductRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void changeMenuDisplay(final UUID productId, final BigDecimal productPrice) {
        final ToBeProduct product = toBeProductRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(productPrice);
        ProductPriceChangeRequest productPriceChangeRequest = new ProductPriceChangeRequest(productId);
        applicationEventPublisher.publishEvent(new ProductPriceChangeEvent(productPriceChangeRequest));
    }

}
