package kitchenpos.menu.domain.event;

import kitchenpos.menu.domain.service.MenuUpdatePolicy;
import kitchenpos.global.event.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceChangeListener {

    private final MenuUpdatePolicy menuUpdatePolicy;

    public ProductPriceChangeListener(MenuUpdatePolicy menuUpdatePolicy) {
        this.menuUpdatePolicy = menuUpdatePolicy;
    }

    @EventListener
    public void handleProductPriceChange(ProductPriceChangedEvent event) {
        menuUpdatePolicy.hideMenu(event.productId());
    }
}
