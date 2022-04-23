package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

	@DisplayName("가격을 생성한다")
	@ValueSource(strings = {"0", "1", "1000"})
	@ParameterizedTest
	void create(BigDecimal value) {
		// when
		Price result = new Price(value);

		// then
		assertThat(result).isNotNull();
	}

	@DisplayName("가격 생성 시 0원 이하인 경우 예외가 발생한다")
	@ValueSource(strings = {"-1", "-100"})
	@ParameterizedTest
	void minusPrice(BigDecimal value) {
		// when then
		assertThatThrownBy(() -> new Price(value))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("가격은 0원 이상이어야 합니다");
	}

	@DisplayName("가격을 더할 수 있다")
	@CsvSource({
			"0, 2, 2",
			"10, 120, 130"
	})
	@ParameterizedTest
	void add(BigDecimal firstStr, BigDecimal secondStr, BigDecimal expectedStr) {
		// given
		Price first = new Price(firstStr);
		Price second = new Price(secondStr);
		Price expected = new Price(expectedStr);

		// when
		Price result = first.add(second);

		// then
		assertThat(result).isEqualTo(expected);
	}

	@DisplayName("가격을 곱할 수 있다")
	@CsvSource({
			"0, 2, 0",
			"11, 120, 1320"
	})
	@ParameterizedTest
	void multiply(BigDecimal firstStr, int count, BigDecimal expectedStr) {
		// given
		Price input = new Price(firstStr);
		Price expected = new Price(expectedStr);

		// when
		Price result = input.multiply(count);

		// then
		assertThat(result).isEqualTo(expected);
	}

}
