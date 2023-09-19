package kitchenpos.menus.application;

import kitchenpos.products.application.ProductChangePriceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

    final MenuService menuService;

    public ProductEventListener(final MenuService menuService) {
        this.menuService = menuService;
    }

    @EventListener
    public void changeDisplayed(final ProductChangePriceEvent event) {
        menuService.changeDisplayed(event.getProductId());
    }
}
