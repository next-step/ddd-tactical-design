package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeForbiddenWordCheckPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

	private FakeForbiddenWordCheckPolicy fakeForbiddenWordCheckPolicy;
	
	@BeforeEach
	void setUp() {
		fakeForbiddenWordCheckPolicy = new FakeForbiddenWordCheckPolicy();
	}
	
	@DisplayName("상품 이름을 생성할 수 있다")
	@Test
	void createProductName() {
		// given
		String name = "라면";

		// when
		ProductName result = new ProductName(name, fakeForbiddenWordCheckPolicy);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(name);
	}

	@DisplayName("상품 이름을 생성 시 이름이 빈값인 경우 예외가 발생한다")
	@ParameterizedTest
	@NullAndEmptySource
	void createProductNameEnpty(String name) {
		// when then
		assertThatThrownBy(() -> new ProductName(name, fakeForbiddenWordCheckPolicy))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("상품 이름은 비워둘 수 없습니다.");
	}

	@DisplayName("상품 이름을 생성 시 금칙어가 존재하는 경우 예외가 발생한다")
	@Test
	void createProductNameForbiddenWord() {
		// given
		String forbiddenName = fakeForbiddenWordCheckPolicy.findAnyForbiddenWord();

		// when then
		assertThatThrownBy(() -> new ProductName(forbiddenName, fakeForbiddenWordCheckPolicy))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("상품 이름에 금칙어가 포함될 수 없습니다.");
	}
}
