package kitchenpos.ordertable.tobe.domain.service;

import kitchenpos.ordertable.tobe.domain.OrderTable;

public interface OrderTableRelatedOrderStatusCheckService {

  boolean hasInCompletedOrders(OrderTable orderTable);

}
