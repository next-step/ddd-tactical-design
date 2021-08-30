package kitchenpos.common.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PriceTest {
	@ParameterizedTest
	@ValueSource(longs = {0L, 17_000L})
	void 가격은_0원_이상이다(long value) {
		// given & when
		Price price = new Price(value);

		// then
		Assertions.assertThat(price.getValue()).isEqualTo(value);
	}

	@ParameterizedTest
	@ValueSource(longs = {-10_000L})
	void 가격은_음수일_수_없다(long value) {
		// given & when & then
		Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new Price(value));
	}

	@Test
	void 가격_동등성() {
		// given
		long value = 17_000L;

		// when
		Price price = new Price(value);

		// then
		Assertions.assertThat(price).isEqualTo(new Price(17_000L));
	}
}
