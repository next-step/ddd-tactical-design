package kitchenpos.menus.application;

import kitchenpos.products.application.MenuServiceClient;
import kitchenpos.products.application.ProductChangePriceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductChangePriceListener {
    final MenuServiceClient menuServiceClient;

    public ProductChangePriceListener(MenuServiceClient menuServiceClient) {
        this.menuServiceClient = menuServiceClient;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void hideMenuHandler(final ProductChangePriceEvent event) {
        menuServiceClient.hideMenuBasedOnProductPrice(event.productId(), event.price().priceValue());
    }
}
