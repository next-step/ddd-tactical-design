package kitchenpos.products.tobe.domain;

import java.util.UUID;

public interface ProductProducer {
    void publish(UUID productId);
}
