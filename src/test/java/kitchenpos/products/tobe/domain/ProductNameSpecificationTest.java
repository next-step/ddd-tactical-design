package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.domain.Name;
import kitchenpos.products.application.FakePurgomalumClient;

class ProductNameSpecificationTest {

	private ProductNameSpecification specification;

	@BeforeEach
	void setUp() {
		specification = new ProductNameSpecification(new FakePurgomalumClient());
	}

	@DisplayName("상품 이름 생성 시 ")
	@Nested
	class NameSpecificationTest {

		@DisplayName("명세를 만족하면 정상적으로 생성된다.")
		@Test
		void construct() {
			// given
			String name = "test";

			// when
			Name productName = new Name(name, specification);

			// then
			assertThat(productName.getValue()).isEqualTo(name);
		}

		@DisplayName("빈 값을 허용하지 않는다.")
		@ParameterizedTest(name = "{displayName} {index} => name={0}")
		@NullAndEmptySource
		void constructFailByEmptySource(String name) {
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new Name(name, specification))
				.withMessage(ExceptionMessages.Product.NAME_IS_EMPTY);
		}

		@DisplayName("비속어를 포함할 수 없다.")
		@ParameterizedTest(name = "{displayName} {index} => name={0}")
		@ValueSource(strings = { "비속어", "욕설" })
		void constructFailByProfanity(String name) {
			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> new Name(name, specification))
				.withMessage(ExceptionMessages.Product.NAME_IS_PROFANITY);
		}
	}
}