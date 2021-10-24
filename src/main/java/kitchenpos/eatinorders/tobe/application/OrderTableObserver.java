package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.CompleteEvent;

public interface OrderTableObserver {
    void orderComplete(CompleteEvent event);
}
