package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.UbiquitousLanguages;

class OrderLineItemsTest {

	@DisplayName("주문 상품 목록은 추가하려는 주문 상품에 ")
	@Nested
	class CreateTest {

		@DisplayName("이상이 없다면 정상적으로 생성된다.")
		@Test
		void create() {
			// given
			OrderLineItem orderLineItem = OrderFixtures.orderLineItem();

			// then
			assertDoesNotThrow(() -> new OrderLineItems(orderLineItem));
		}

		@DisplayName("이상이 있다면 (빈 값이라면) 예외가 발생한다.")
		@ParameterizedTest
		@NullSource
		void createFailByEmptyValue(OrderLineItem source) {
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new OrderLineItems(source))
				.withMessage(String.format(ExceptionMessages.EMPTY_INVENTORY_TEMPLATE, UbiquitousLanguages.ORDER_LINE_ITEM));
		}
	}
}