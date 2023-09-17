package kitchenpos.products.tobe.domain;

import kitchenpos.support.product.event.ProductPriceChangedEvent;
import kitchenpos.support.product.vo.ProductPrice;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductDomainService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductDomainService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public boolean changePrice(Product product, BigDecimal price) {
        boolean isChanged = product.changePrice(ProductPrice.create(price));
        if (isChanged) {
            applicationEventPublisher.publishEvent(new ProductPriceChangedEvent(
                    product.getId(),
                    product.getName(),
                    product.getPrice()
            ));
        }
        return isChanged;
    }

}
