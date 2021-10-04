package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.ChangePriceEvent;

public interface ProductObserver {
    void changeProductPrice(ChangePriceEvent event);
}
