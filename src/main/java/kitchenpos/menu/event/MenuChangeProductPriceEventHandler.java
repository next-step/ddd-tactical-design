package kitchenpos.menu.event;

import java.util.List;
import kitchenpos.common.Price;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MenuChangeProductPriceEventHandler {

    private final MenuRepository menuRepository;

    public MenuChangeProductPriceEventHandler(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Async
    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT,
        fallbackExecution = true
    )
    public void handleChangeProductPriceEvent(ChangeProductPriceEvent event) {
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        menus.forEach(menu -> menu.changeMenuProductPrice(event.getProductId(), Price.of(event.getPrice())));
    }
}
