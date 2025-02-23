package kitchenpos.menu.domain.event;

import kitchenpos.global.event.ProductEvent;
import kitchenpos.menu.domain.service.MenuPolicy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DefaultProductEventListener implements ProductEventListener {

    private final MenuPolicy menuPolicy;

    public DefaultProductEventListener(MenuPolicy menuPolicy) {
        this.menuPolicy = menuPolicy;
    }

    @EventListener
    @Override
    public void handle(ProductEvent event) {
        if (event instanceof ProductEvent.ProductPriceChangedEvent priceChangedEvent) {
            menuPolicy.hideMenu(priceChangedEvent.productId());
        }
    }
}
