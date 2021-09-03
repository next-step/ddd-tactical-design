package kitchenpos.products.tobe.domain.interfaces.event;

import kitchenpos.products.tobe.ui.dto.ProductChangePriceEvent;

public interface EventPublishClient {
    void publishEvent(ProductChangePriceEvent event);
}
