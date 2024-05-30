package kitchenpos.menus.application;

import kitchenpos.support.application.MenuServiceClient;
import kitchenpos.menus.tobe.domain.menu.ProductClient;
import kitchenpos.products.application.ProductChangePriceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class ProductChangePriceListener {
    final MenuServiceClient menuServiceClient;
    final ProductClient productClient;

    public ProductChangePriceListener(MenuServiceClient menuServiceClient, ProductClient productClient) {
        this.menuServiceClient = menuServiceClient;
        this.productClient = productClient;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void hideMenuHandler(final ProductChangePriceEvent event) {
        UUID productId = event.productId();
        menuServiceClient.hideMenuBasedOnProductPrice(productId, productClient.productPrice(productId));
    }
}
