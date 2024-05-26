package kitchenpos.common.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.Quantity;

class QuantityTest {

	@ParameterizedTest
	@ValueSource(longs = {-1, -10, -100})
	@DisplayName("수량이 0 미만이면 수량 객체를 생성할 수 없다.")
	void quantityLessThanZero(long invalidQuantity) {
		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new Quantity(invalidQuantity))
			.withMessage(Quantity.INVALID_VALUE_ERROR);
	}

	@Test
	@DisplayName("유효한 수량을 입력하면 수량 객체가 정상적으로 생성된다.")
	void validQuantity() {
		// Given
		long validQuantity = 10;

		// When
		Quantity quantity = new Quantity(validQuantity);

		// Then
		assertEquals(validQuantity, quantity.getValue());
	}
}
