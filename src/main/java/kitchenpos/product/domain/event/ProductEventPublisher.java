package kitchenpos.product.domain.event;

import kitchenpos.global.event.ProductEvent;

public interface ProductEventPublisher {
    void publish(ProductEvent event);
}
