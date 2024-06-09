package kitchenpos.menus.tobe.application;

import kitchenpos.common.domainevent.event.ProductPriceChanged;
import kitchenpos.menus.tobe.domain.application.HideMenuWithInvalidPriceByProductId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MenuEventListener {
    private final HideMenuWithInvalidPriceByProductId hideMenuWithInvalidPriceByProductId;

    public MenuEventListener(HideMenuWithInvalidPriceByProductId hideMenuWithInvalidPriceByProductId) {
        this.hideMenuWithInvalidPriceByProductId = hideMenuWithInvalidPriceByProductId;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProductPriceChanged(ProductPriceChanged event) {
        hideMenuWithInvalidPriceByProductId.execute(event.getProductId());
    }
}
