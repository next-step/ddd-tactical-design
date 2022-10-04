package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.Specs;
import kitchenpos.core.constant.UbiquitousLanguages;
import kitchenpos.core.domain.Price;
import kitchenpos.core.specification.PriceSpecification;

class ProductPriceSpecificationTest {

	private PriceSpecification specification;

	@BeforeEach
	void setUp() {
		specification = Specs.Product.PRICE;
	}

	@DisplayName("상품 가격 생성 시 ")
	@Nested
	class PriceSpecificationTest {

		@DisplayName("명세를 만족하면 정상적으로 생성된다.")
		@Test
		void construct() {
			// given
			BigDecimal price = BigDecimal.valueOf(1000);

			// when
			Price result = new Price(price, specification);

			// then
			assertThat(result.getValue()).isEqualTo(price);
		}

		@DisplayName("빈 값을 허용하지 않는다.")
		@ParameterizedTest(name = "{displayName} {index} => price={0}")
		@NullSource
		void constructFailByEmptySource(BigDecimal value) {
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new Price(value, specification))
				.withMessage(String.format(ExceptionMessages.EMPTY_PRICE_TEMPLATE, UbiquitousLanguages.PRODUCT));
		}

		@DisplayName("0보다 작은 값을 가질 수 없다.")
		@ParameterizedTest(name = "{displayName} {index} => price={0}")
		@ValueSource(longs = { -1000, -2000 })
		void constructFailByProfanity(long longValue) {
			// given
			BigDecimal value = BigDecimal.valueOf(longValue);

			// then
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new Price(value, specification))
				.withMessage(String.format(ExceptionMessages.NEGATIVE_PRICE_TEMPLATE, UbiquitousLanguages.PRODUCT));
		}
	}
}
