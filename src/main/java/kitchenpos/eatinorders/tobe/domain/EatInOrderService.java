package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.core.annotation.CommandDomainService;
import kitchenpos.core.constant.ExceptionMessages;

@CommandDomainService
public class EatInOrderService {

	public EatInOrder order(OrderLineItems orderLineItems, OrderTable orderTable) {
		if (orderTable.isUnoccupied()) {
			throw new IllegalArgumentException(ExceptionMessages.Order.EMPTY_ORDER_TABLE);
		}

		return new EatInOrder(orderLineItems, orderTable.getId());
	}
}
