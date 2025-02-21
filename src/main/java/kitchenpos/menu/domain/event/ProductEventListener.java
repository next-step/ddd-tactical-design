package kitchenpos.menu.domain.event;

import kitchenpos.global.event.ProductEvent;

public interface ProductEventListener {
    void handle(ProductEvent event);
}
