package kitchenpos.menu.tobe.application.listener;

import kitchenpos.menu.tobe.application.MenuService;
import kitchenpos.menu.tobe.application.dto.request.MenuPriceChangeRequest;
import kitchenpos.product.tobe.application.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductPriceEventListener {
    final MenuService menuService;

    public ProductPriceEventListener(MenuService menuService) {
        this.menuService = menuService;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void hideMenuHandler(final ProductPriceChangedEvent event) {
        menuService.changePrice(event.productId(), MenuPriceChangeRequest.of(event.price()));
    }
}

