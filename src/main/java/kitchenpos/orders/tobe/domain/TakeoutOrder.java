package kitchenpos.orders.tobe.domain;

import java.time.LocalDateTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeoutOrder extends Order {

	protected TakeoutOrder() {
		super(OrderType.TAKEOUT);
	}

	public TakeoutOrder(
		OrderStatus status,
		LocalDateTime orderDateTime,
		OrderLineItems orderLineItems
	) {
		super(OrderType.TAKEOUT, status, orderDateTime, orderLineItems, null);
	}

	@Override
	public Order completed() {
		if (status != OrderStatus.SERVED) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}

		status = OrderStatus.COMPLETED;

		return this;
	}
}
