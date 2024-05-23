package kitchenpos.order.domain;

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
	public Order accepted(KitchenridersClient kitchenridersClient) {
		if (status != OrderStatus.WAITING) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.ACCEPTED;
		return this;
	}

	@Override
	public Order served() {
		if (status != OrderStatus.ACCEPTED) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.SERVED;
		return this;
	}

	@Override
	public Order delivering() {
		throw new UnsupportedOperationException("포장 주문에는 배달 상태가 없습니다.");
	}

	@Override
	public Order delivered() {
		throw new UnsupportedOperationException("포장 주문에는 배달 상태가 없습니다.");
	}

	@Override
	public Order completed(OrderRepository orderRepository) {
		if (status != OrderStatus.SERVED) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.COMPLETED;
		return this;
	}
}
