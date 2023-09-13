package kitchenpos.product.application.port.out;

import java.util.UUID;

public interface ProductPriceChangeEventPublisher {
    
    void publish(final UUID id);
}
