package kitchenpos.menu.domain.event;

import kitchenpos.global.event.ProductEvent;
import kitchenpos.menu.domain.service.MenuPolicy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DefaultProductEventListener implements ProductEventListener {

    private final MenuPolicy menuPolicy;

    public DefaultProductEventListener(MenuPolicy menuPolicy) {
        this.menuPolicy = menuPolicy;
    }

    @TransactionalEventListener
    @Override
    public void handle(ProductEvent event) {
        if (event instanceof ProductEvent.ProductPriceChangedEvent priceChangedEvent) {
            menuPolicy.hideMenu(priceChangedEvent.productId());
        }
    }
}
