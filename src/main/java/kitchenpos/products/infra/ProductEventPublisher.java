package kitchenpos.products.infra;

import java.util.UUID;

public interface ProductEventPublisher {
    void publishChangePriceEvent(UUID productID);
}
