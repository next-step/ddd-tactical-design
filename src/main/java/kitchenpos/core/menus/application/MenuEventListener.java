package kitchenpos.core.menus.application;

import jakarta.transaction.Transactional;
import kitchenpos.core.menus.domain.Menu;
import kitchenpos.core.menus.domain.MenuRepository;
import kitchenpos.core.shared.event.ProductPriceChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MenuEventListener {
    private final MenuRepository menuRepository;

    public MenuEventListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onProductPriceChanged(final ProductPriceChangedEvent event) {
        menuRepository.findAllByProductId(event.id())
                .forEach(Menu::recalculateDisplayStatus);
    }
}
