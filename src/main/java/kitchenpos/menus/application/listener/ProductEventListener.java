package kitchenpos.menus.application.listener;

import kitchenpos.menus.application.menu.MenuService;
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
        menuService.hideIfMenuPriceGraterThanProducts(event.getProductId());
    }
}
