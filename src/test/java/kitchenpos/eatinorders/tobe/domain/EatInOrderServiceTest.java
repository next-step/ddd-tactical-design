package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.core.constant.ExceptionMessages;

class EatInOrderServiceTest {

	private EatInOrderService eatInOrderService;

	@BeforeEach
	void setUp() {
		eatInOrderService = new EatInOrderService();
	}

	@DisplayName("손님이 착석한 주문 테이블에서 주문을 생성할 수 있다.")
	@Test
	void construct() {
		// given
		OrderLineItems orderLineItems = OrderFixtures.orderLineItems();
		OrderTable orderTable = OrderFixtures.occupiedOrderTable(3);

		// when
		EatInOrder eatInOrder = eatInOrderService.order(orderLineItems, orderTable);

		// then
		assertThat(eatInOrder).isNotNull();
	}

	@DisplayName("빈 주문 테이블에서 주문을 생성할 수 없다.")
	@Test
	void constructFailByEmptyOrderTable() {
		// given
		OrderLineItems orderLineItems = OrderFixtures.orderLineItems();
		OrderTable orderTable = OrderFixtures.unoccupiedOrderTable();

		// when, then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> eatInOrderService.order(orderLineItems, orderTable))
			.withMessage(ExceptionMessages.Order.EMPTY_ORDER_TABLE);
	}
}