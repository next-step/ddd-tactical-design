package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.OrderCompleteEvent;

public interface OrderTableObserver {
    void orderComplete(OrderCompleteEvent event);
}
