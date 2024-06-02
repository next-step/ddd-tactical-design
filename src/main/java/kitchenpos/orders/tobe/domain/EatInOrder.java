package kitchenpos.orders.tobe.domain;

import static kitchenpos.orders.tobe.domain.OrderTable.*;

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

		if (!orderTable.isOccupied()) {
			throw new IllegalStateException(NOT_OCCUPIED_ORDER_TABLE_ERROR);
		}
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
