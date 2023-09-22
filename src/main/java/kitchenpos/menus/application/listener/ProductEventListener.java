package kitchenpos.menus.application.listener;

import kitchenpos.menus.application.menu.MenuService;
import kitchenpos.products.application.ProductChangePriceEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProductEventListener {

    final MenuService menuService;

    public ProductEventListener(final MenuService menuService) {
        this.menuService = menuService;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeDisplayed(final ProductChangePriceEvent event) {
        menuService.hideIfMenuPriceGraterThanProduct(event.getProductId());
    }
}
