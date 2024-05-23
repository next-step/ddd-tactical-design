package kitchenpos.order.domain;

import java.time.LocalDateTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {
	private static final String ORDER_TABLE_NOT_FOUND_ERROR = "주문 테이블을 찾을 수 없습니다.";

	protected EatInOrder() {
		super(OrderType.EAT_IN);
	}

	public EatInOrder(
		OrderStatus status,
		LocalDateTime orderDateTime,
		OrderLineItems orderLineItems,
		OrderTable orderTable
	) {
		super(OrderType.EAT_IN, status, orderDateTime, orderLineItems, orderTable);

		if (orderTable == null) {
			throw new IllegalArgumentException(ORDER_TABLE_NOT_FOUND_ERROR);
		}
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
		throw new UnsupportedOperationException("매장 주문에는 배달 상태가 없습니다.");
	}

	@Override
	public Order delivered() {
		throw new UnsupportedOperationException("매장 주문에는 배달 상태가 없습니다.");
	}

	@Override
	public Order completed(OrderRepository orderRepository) {
		if (status != OrderStatus.SERVED) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.COMPLETED;
		if (!orderRepository.existsByOrderTableAndStatusNot(getOrderTable(), OrderStatus.COMPLETED)) {
			getOrderTable().used(false);
		}
		return this;
	}
}
