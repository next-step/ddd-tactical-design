package kitchenpos.menus.application;

import kitchenpos.common.acl.menu.MenuServiceClient;
import kitchenpos.products.application.ProductChangePriceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductChangePriceListener {
    final MenuServiceClient menuServiceClient;

    public ProductChangePriceListener(MenuServiceClient menuServiceClient) {
        this.menuServiceClient = menuServiceClient;
    }

    @EventListener
    public void hideMenuHandler(final ProductChangePriceEvent event) {
        menuServiceClient.hideMenuBasedOnProductPrice(event.productId());
    }
}
