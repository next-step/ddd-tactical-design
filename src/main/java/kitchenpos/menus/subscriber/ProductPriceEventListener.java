package kitchenpos.menus.subscriber;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.publisher.ProductPriceChangedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProductPriceEventListener {

    private final MenuService menuService;

    public ProductPriceEventListener(MenuService menuService) {
        this.menuService = menuService;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void listen(ProductPriceChangedEvent event) {
        menuService.hideMenuWhenChangeProductPrice(event.getProductId());
    }
}
