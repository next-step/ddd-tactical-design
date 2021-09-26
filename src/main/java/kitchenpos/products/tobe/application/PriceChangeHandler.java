package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.products.tobe.domain.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeHandler {

    private final MenuService menuService;

    public PriceChangeHandler(final MenuService menuService) {
        this.menuService = menuService;
    }

    @EventListener
    public void checkHideMenu(final ProductPriceChangedEvent event) {
        menuService.checkAllHideByProductId(event.getProductId());
    }
}
