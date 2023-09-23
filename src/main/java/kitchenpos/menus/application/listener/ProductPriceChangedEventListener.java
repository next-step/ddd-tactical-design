package kitchenpos.menus.application.listener;

import kitchenpos.common.events.ProductPriceChangedEvent;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.tobe.domain.menu.ProductId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProductPriceChangedEventListener {

    private final MenuService menuService;

    public ProductPriceChangedEventListener(MenuService menuService) {
        this.menuService = menuService;
    }


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void listen(ProductPriceChangedEvent event) {
        ProductId productId = new ProductId(event.getProductId());
        menuService.checkHideAndPrice(productId);
    }

}
