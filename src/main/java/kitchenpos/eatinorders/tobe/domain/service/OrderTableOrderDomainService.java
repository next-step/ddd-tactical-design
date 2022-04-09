package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.ordertable.tobe.domain.OrderTable;

public interface OrderTableOrderDomainService {

  boolean hasInCompletedOrders(OrderTable orderTable);

}
