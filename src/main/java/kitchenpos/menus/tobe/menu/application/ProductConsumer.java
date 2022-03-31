package kitchenpos.menus.tobe.menu.application;

import kitchenpos.common.domain.ProductChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    private final MenuService menuService;

    @Autowired
    public ProductConsumer(final MenuService menuService) {
        this.menuService = menuService;
    }

    @EventListener
    public void checkHideMenu(final ProductChangedEvent event) {
        menuService.checkAllHideByProductId(event.getProductId());
    }
}
