package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeHandler {

    @EventListener(classes = ProductPriceChangedEvent.class)
    public void handle() {

    }
}
