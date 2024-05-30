package kitchenpos.menus.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MenuProductEventHandler {

    private final MenuRepository menuRepository;

    public MenuProductEventHandler(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener
    public void productPriceChangeEvent(ProductPriceChangeEvent productEvent) {
        menuRepository
                .findAllByProductId(productEvent.productId())
                .forEach(Menu::updateDisplayStatusOnPriceChange);
    }
}
