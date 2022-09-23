package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

	@DisplayName("상품을 등록할 수 있다.")
	@Test
	void create() {
		final Product product = new Product("후라이드", BigDecimal.valueOf(16_000L));

		assertThat(product).isNotNull();
		assertAll(
				() -> assertThat(product.getId()).isNotNull(),
				() -> assertThat(product.getName()).isEqualTo(product.getName()),
				() -> assertThat(product.getPrice()).isEqualTo(product.getPrice())
		);
	}

	@DisplayName("상품 등록 에러")
	@Nested
	class ErrorTest {

		@DisplayName("이름이 올바르지 않으면 등록할 수 없다.")
		@ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
		@NullSource
		@ParameterizedTest
		void create1(final String name) {
			assertThatThrownBy(() -> new Product(name, BigDecimal.valueOf(16_000L)))
					.isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("가격이 올바르지 않으면 등록할 수 없다.")
		@ValueSource(strings = "-1000")
		@NullSource
		@ParameterizedTest
		void create2(final BigDecimal price) {
			assertThatThrownBy(() -> new Product("후라이드", price))
					.isInstanceOf(IllegalArgumentException.class);
		}

		@DisplayName("가격이 올바르지 않으면 변경할 수 없다.")
		@ValueSource(strings = "-1000")
		@NullSource
		@ParameterizedTest
		void changePrice(final BigDecimal price) {
			final Product product = new Product("후라이드", BigDecimal.valueOf(16_000L));

			assertThatThrownBy(() -> product.changePrice(price))
					.isInstanceOf(IllegalArgumentException.class);
		}
	}
}
