package kitchenpos.menu.event;

import java.util.List;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuProductRepository;
import kitchenpos.menu.tobe.domain.MenuRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MenuChangeProductPriceEventHandler {

    private final MenuRepository menuRepository;
    private final MenuProductRepository menuProductRepository;

    public MenuChangeProductPriceEventHandler(MenuRepository menuRepository, MenuProductRepository menuProductRepository) {
        this.menuRepository = menuRepository;
        this.menuProductRepository = menuProductRepository;
    }

    @Async
    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT,
        fallbackExecution = true
    )
    public void handleChangeProductPriceEvent(ChangeProductPriceEvent event) {
        menuProductRepository.bulkUpdateMenuProductPrice(event.getProductId(), event.getPrice());

        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        menus.forEach(Menu::checkPrice);
    }
}
