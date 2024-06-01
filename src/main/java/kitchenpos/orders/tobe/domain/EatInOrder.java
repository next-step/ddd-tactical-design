package kitchenpos.orders.tobe.domain;

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
	public Order completed(OrderRepository orderRepository) {
		super.completed(orderRepository);

		if (!orderRepository.existsByOrderTableAndStatusNot(getOrderTable(), OrderStatus.COMPLETED)) {
			getOrderTable().used(false);
		}

		return this;
	}
}
