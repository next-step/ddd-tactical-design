package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;

public interface OrderTableOrderDomainService {

  boolean hasInCompletedOrders(OrderTable orderTable);

}
