package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {
	@ParameterizedTest
	@ValueSource(strings = {"0", "17000"})
	void 가격을_생성할_수_있다(BigDecimal value) {
		// given & when
		Price price = new Price(value);

		// then
		assertThat(price.getValue()).isEqualTo(value);
	}

	@ParameterizedTest
	@ValueSource(strings = {"-10000"})
	@NullSource
	void 빈_값이거나_음수일_경우_가격을_생성할_수_없다(BigDecimal value) {
		// given & when & then
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Price(value));
	}

	@ParameterizedTest
	@CsvSource({"10000, 15000, 25000", "18000, 2000, 20000"})
	void 덧셈(long aValue, long bValue, long cValue) {
		// given
		Price a = new Price(new BigDecimal(aValue));
		Price b = new Price(new BigDecimal(bValue));

		// when
		Price c = Price.add(a, b);

		// then
		assertThat(c).isEqualTo(new Price(new BigDecimal(cValue)));
	}

	@ParameterizedTest
	@CsvSource({"10000, 2, 20000", "15000, 3, 45000"})
	void 곱셈(long aValue, long bValue, long cValue) {
		// given
		Price a = new Price(new BigDecimal(aValue));

		// when
		Price c = Price.multiply(a, bValue);

		// then
		assertThat(c).isEqualTo(new Price(new BigDecimal(cValue)));
	}

	@Test
	void 동등성() {
		// given
		long value = 10000L;

		// when
		final Price price1 = new Price(new BigDecimal(value));
		final Price price2 = new Price(new BigDecimal(value));

		// then
		assertThat(price1).isEqualTo(price2);
	}
}
