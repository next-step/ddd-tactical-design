package kitchenpos.order.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberOfGuestsTest {

	@ParameterizedTest
	@ValueSource(ints = {-1, -10, -100})
	@DisplayName("손님 수가 0 미만이면 객체를 생성할 수 없다.")
	void numberOfGuestsLessThanZero(int invalidNumberOfGuests) {
		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new NumberOfGuests(invalidNumberOfGuests))
			.withMessage(NumberOfGuests.INVALID_VALUE_ERROR);
	}

	@Test
	@DisplayName("유효한 손님 수를 입력하면 객체가 정상적으로 생성된다.")
	void validNumberOfGuests() {
		// Given
		int validNumberOfGuests = 10;

		// When
		NumberOfGuests numberOfGuests = new NumberOfGuests(validNumberOfGuests);

		// Then
		assertEquals(validNumberOfGuests, numberOfGuests.getValue());
	}
}