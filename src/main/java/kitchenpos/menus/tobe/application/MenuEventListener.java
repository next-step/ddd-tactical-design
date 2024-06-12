package kitchenpos.menus.tobe.application;

import kitchenpos.common.domainevent.event.ProductPriceChanged;
import kitchenpos.menus.tobe.domain.application.ProductPriceChangedEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MenuEventListener {
    private final ProductPriceChangedEventHandler productPriceChangedEventHandler;

    public MenuEventListener(ProductPriceChangedEventHandler productPriceChangedEventHandler) {
        this.productPriceChangedEventHandler = productPriceChangedEventHandler;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProductPriceChanged(ProductPriceChanged event) {
        productPriceChangedEventHandler.changeMenuProductPriceAndHide(event.getProductId());
    }
}
