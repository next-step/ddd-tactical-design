package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

public interface OrderTableOrders {
    boolean existByOrderTableId(OrderTableId orderTableId);
}
