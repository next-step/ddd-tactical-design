package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.Specs;
import kitchenpos.core.constant.UbiquitousLanguages;
import kitchenpos.core.domain.Name;

class OrderTableTest {

	@DisplayName("주문 테이블 생성 시 ")
	@Nested
	class CreateTest {

		@DisplayName("기본 상태는 미점유로 생성된다.")
		@Test
		void create() {
			// given
			Name name = new Name(OrderFixtures.ORDER_TABLE_NAME, Specs.OrderTable.NAME);

			// when
			OrderTable orderTable = new OrderTable(name);

			// then
			assertAll(
				() -> assertThat(orderTable.getId()).isNotNull(),
				() -> assertThat(orderTable.getName()).isNotNull(),
				() -> assertThat(orderTable.isOccupied()).isFalse(),
				() -> assertThat(orderTable.getNumberOfGuests()).isZero()
			);
		}
	}

	@DisplayName("주문 테이블 고객 수 변경 시 ")
	@Nested
	class ChangeNumberOfGuestsTest {

		@DisplayName("점유 상태로 변경은 고객 수가 0명 이상이어야 가능하다.")
		@ParameterizedTest
		@ValueSource(ints = { 0, 100, 500, 1000 })
		void sit(int numberOfGuests) {
			// given
			OrderTable orderTable = OrderFixtures.unoccupiedOrderTable();

			// when
			orderTable.sit(numberOfGuests);

			// then
			assertAll(
				() -> assertThat(orderTable.isOccupied()).isTrue(),
				() -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(numberOfGuests)
			);
		}

		@DisplayName("점유 상태로 변경은 고객 수가 0명 미만이라면 예외가 발생한다.")
		@Test
		void sitFailByNegativeNumberOfGuests() {
			// given
			OrderTable orderTable = OrderFixtures.unoccupiedOrderTable();

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> orderTable.sit(-1))
				.withMessage(String.format(ExceptionMessages.NEGATIVE_QUANTITY_TEMPLATE, UbiquitousLanguages.NUMBER_OF_GUESTS));

			assertThat(orderTable.isOccupied()).isFalse();
		}

		@DisplayName("미점유 상태로 변경은 고객 수를 0명으로 바꾼다.")
		@Test
		void clear() {
			// given
			OrderTable orderTable = OrderFixtures.occupiedOrderTable(10);

			// when
			orderTable.clear();

			// then
			assertAll(
				() -> assertThat(orderTable.isOccupied()).isFalse(),
				() -> assertThat(orderTable.getNumberOfGuests()).isZero()
			);
		}
	}
}