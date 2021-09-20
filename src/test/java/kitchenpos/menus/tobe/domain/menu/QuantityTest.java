package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {
	@ParameterizedTest
	@ValueSource(strings = {"0", "10"})
	void 수량을_생성할_수_있다(long value) {
		// given & when
		Quantity quantity = new Quantity(value);

		// then
		assertThat(quantity.getValue()).isEqualTo(value);
	}

	@ParameterizedTest
	@ValueSource(strings = {"-10", "-1"})
	void 수량은_0_이상이어야_한다(long value) {
		// given & when & then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Quantity(value));
	}

	@Test
	void 동등성() {
		// given
		long value = 10000L;

		// when
		final Quantity quantity1 = new Quantity(value);
		final Quantity quantity2 = new Quantity(value);

		// then
		assertThat(quantity1).isEqualTo(quantity2);
	}
}