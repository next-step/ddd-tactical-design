package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import kitchenpos.core.constant.ExceptionMessages;

class EatInOrderTest {

	@DisplayName("매장 주문 생성 시 ")
	@Nested
	class CreateTest {

		@DisplayName("기본 주문 상태는 WAITING 으로 생성된다.")
		@Test
		void create() {
			// given
			EatInOrder eatInOrder = new EatInOrder(OrderFixtures.orderLineItems(), OrderFixtures.occupiedOrderTable(1));

			// then
			assertAll(
				() -> assertThat(eatInOrder.getId()).isNotNull(),
				() -> assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.WAITING),
				() -> assertThat(eatInOrder.getOrderDateTime()).isNotNull(),
				() -> assertThat(eatInOrder.getOrderLineItems()).isNotNull()
			);
		}

		@DisplayName("주문 테이블이 빈 상태라면 예외가 발생한다.")
		@Test
		void createFailByEmptyOrderTable() {
			// given
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new EatInOrder(OrderFixtures.orderLineItems(), OrderFixtures.unoccupiedOrderTable()))
				.withMessage(ExceptionMessages.Order.EMPTY_ORDER_TABLE);
		}
	}

	@DisplayName("주문 상태 변경 시 ")
	@Nested
	class ChangeOrderStatusTest {

		@DisplayName("주문 수락은 WAITING -> ACCEPT 상태로 변경된다.")
		@Test
		void accept() {
			// given
			EatInOrder eatInOrder = OrderFixtures.eatInOrder(OrderStatus.WAITING);

			// when
			eatInOrder.accept();

			// then
			assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
		}

		@DisplayName("주문 수락은 주문 상태가 WAITING 가 아니면 예외가 발생한다.")
		@ParameterizedTest(name = "{displayName}, status = {0}")
		@EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
		void acceptFailBy(OrderStatus status) {
			// given
			EatInOrder eatInOrder = OrderFixtures.eatInOrder(status);

			// when then
			assertThatExceptionOfType(IllegalStateException.class)
				.isThrownBy(eatInOrder::accept)
				.withMessage(ExceptionMessages.Order.INVALID_ORDER_STATUS);
		}

		@DisplayName("주문 제공은 ACCEPT -> SERVED 상태로 변경된다.")
		@Test
		void serve() {
			// given
			EatInOrder eatInOrder = OrderFixtures.eatInOrder(OrderStatus.ACCEPTED);

			// when
			eatInOrder.serve();

			// then
			assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
		}

		@DisplayName("주문 제공은 주문 상태가 ACCEPT 가 아니면 예외가 발생한다.")
		@ParameterizedTest(name = "{displayName}, status = {0}")
		@EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
		void serveFailBy(OrderStatus status) {
			// given
			EatInOrder eatInOrder = OrderFixtures.eatInOrder(status);

			// when then
			assertThatExceptionOfType(IllegalStateException.class)
				.isThrownBy(eatInOrder::serve)
				.withMessage(ExceptionMessages.Order.INVALID_ORDER_STATUS);
		}

		@DisplayName("주문 완료는 SERVED -> COMPLETED 상태로 변경된다.")
		@Test
		void complete() {
			// given
			EatInOrder eatInOrder = OrderFixtures.eatInOrder(OrderStatus.SERVED);

			// when
			eatInOrder.complete();

			// then
			assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
		}

		@DisplayName("주문 완료는 주문 상태가 SERVED 가 아니면 예외가 발생한다.")
		@ParameterizedTest(name = "{displayName}, status = {0}")
		@EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
		void completeFailBy(OrderStatus status) {
			// given
			EatInOrder eatInOrder = OrderFixtures.eatInOrder(status);

			// when then
			assertThatExceptionOfType(IllegalStateException.class)
				.isThrownBy(eatInOrder::complete)
				.withMessage(ExceptionMessages.Order.INVALID_ORDER_STATUS);
		}
	}
}