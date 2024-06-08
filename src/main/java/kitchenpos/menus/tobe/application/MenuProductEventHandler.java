package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.TobeMenu;
import kitchenpos.menus.tobe.domain.TobeMenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
public class MenuProductEventHandler {

    private final TobeMenuRepository menuRepository;

    public MenuProductEventHandler(final TobeMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void productPriceChangeEvent(ProductPriceChangeEvent productEvent) {
        menuRepository
                .findAllByProductId(productEvent.productId())
                .forEach(TobeMenu::updateDisplayStatusOnPriceChange);
    }
}
