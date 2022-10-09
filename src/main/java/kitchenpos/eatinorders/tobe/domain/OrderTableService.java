package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.core.annotation.CommandDomainService;
import kitchenpos.core.constant.ExceptionMessages;

@CommandDomainService
public class OrderTableService {

	public void clear(EatInOrder eatInOrder, OrderTable orderTable) {
		if (!eatInOrder.isCompleted())
			throw new IllegalStateException(ExceptionMessages.Order.INVALID_ORDER_STATUS);

		orderTable.clear();
	}
}
