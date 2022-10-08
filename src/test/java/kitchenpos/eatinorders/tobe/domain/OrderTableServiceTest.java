package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import kitchenpos.core.constant.ExceptionMessages;

class OrderTableServiceTest {

	private OrderTableService orderTableService;

	@BeforeEach
	void setUp() {
		orderTableService = new OrderTableService();
	}

	@DisplayName("주문 완료 상태라면 주문 테이블을 정리할 수 있다.")
	@Test
	void clear() {
		// given
		EatInOrder eatInOrder = OrderFixtures.eatInOrder(OrderStatus.COMPLETED);
		OrderTable orderTable = OrderFixtures.occupiedOrderTable(5);

		// when, then
		assertDoesNotThrow(() -> orderTableService.clear(eatInOrder, orderTable));
	}

	@DisplayName("주문 완료 상태가 아니면 주문 테이블은 정리할 수 없다.")
	@ParameterizedTest
	@EnumSource(value = OrderStatus.class, names = { "COMPLETED" }, mode = EnumSource.Mode.EXCLUDE)
	void clearFailByStatus(OrderStatus orderStatus) {
		// given
		EatInOrder eatInOrder = OrderFixtures.eatInOrder(orderStatus);
		OrderTable orderTable = OrderFixtures.occupiedOrderTable(5);

		// when, then
		assertThatExceptionOfType(IllegalStateException.class)
			.isThrownBy(() -> orderTableService.clear(eatInOrder, orderTable))
			.withMessage(ExceptionMessages.Order.INVALID_ORDER_STATUS);
	}
}