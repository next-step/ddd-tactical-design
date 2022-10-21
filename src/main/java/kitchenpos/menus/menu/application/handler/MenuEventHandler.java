package kitchenpos.menus.menu.application.handler;

import kitchenpos.common.domain.vo.Price;
import kitchenpos.common.event.ProductPriceChangedEvent;
import kitchenpos.menus.menu.tobe.domain.Menu;
import kitchenpos.menus.menu.tobe.domain.MenuRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class MenuEventHandler {

    private final MenuRepository menuRepository;

    public MenuEventHandler(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void productPriceChangedEvent(final ProductPriceChangedEvent event) {
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        for (final Menu menu : menus) {
            menu.changeMenuProductPrice(event.getProductId(), Price.valueOf(event.getPrice()));
        }
    }
}
