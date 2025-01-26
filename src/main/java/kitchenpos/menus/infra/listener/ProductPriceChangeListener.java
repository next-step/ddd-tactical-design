package kitchenpos.menus.infra.listener;

import kitchenpos.menus.application.MenuService;
import kitchenpos.shared.event.ProductPriceChangeEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProductPriceChangeListener {

    private final MenuService menuService;

    public ProductPriceChangeListener(MenuService menuService) {
        this.menuService = menuService;
    }

    @TransactionalEventListener
    public void listen(ProductPriceChangeEvent event) {
        menuService.changeProductPrice(event.getId(), event.getProductPrice());
    }
}
