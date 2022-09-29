package kitchenpos.menus.application;

import kitchenpos.event.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MenuEventHandler {

    @Async
    @EventListener
    public void checkMenuPrice(ProductPriceChangedEvent event) {
        // TODO:
    }
}
