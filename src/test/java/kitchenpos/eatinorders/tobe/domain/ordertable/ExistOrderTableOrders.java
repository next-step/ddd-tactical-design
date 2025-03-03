package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

public class ExistOrderTableOrders implements OrderTableOrders {

    @Override
    public boolean existByOrderTableId(final OrderTableId orderTableId) {
        return true;
    }
}
