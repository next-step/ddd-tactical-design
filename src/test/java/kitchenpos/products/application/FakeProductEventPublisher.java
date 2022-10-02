package kitchenpos.products.application;

import java.util.UUID;
import kitchenpos.products.infra.ProductEventPublisher;

public class FakeProductEventPublisher implements ProductEventPublisher {

    @Override
    public void publishChangePriceEvent(UUID productID) {

    }
}
