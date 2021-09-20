package kitchenpos.products.tobe.domain.interfaces.event;

import kitchenpos.products.tobe.domain.model.ProductChangePriceEvent;

public interface EventPublishClient {
    void publishEvent(ProductChangePriceEvent event);
}
