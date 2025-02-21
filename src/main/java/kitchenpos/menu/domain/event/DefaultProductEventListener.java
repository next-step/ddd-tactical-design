package kitchenpos.menu.domain.event;

import kitchenpos.global.event.ProductEvent;
import kitchenpos.menu.domain.service.MenuUpdatePolicy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DefaultProductEventListener implements ProductEventListener {

    private final MenuUpdatePolicy menuUpdatePolicy;

    public DefaultProductEventListener(MenuUpdatePolicy menuUpdatePolicy) {
        this.menuUpdatePolicy = menuUpdatePolicy;
    }

    @EventListener
    @Override
    public void handle(ProductEvent event) {
        if (event instanceof ProductEvent.ProductPriceChangedEvent priceChangedEvent) {
            menuUpdatePolicy.hideMenu(priceChangedEvent.productId());
        }
    }
}
