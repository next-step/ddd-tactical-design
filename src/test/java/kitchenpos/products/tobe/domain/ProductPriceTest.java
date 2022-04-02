package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

	@DisplayName("상품 가격을 생성할 수 있다")
	@ParameterizedTest(name = "상품 가격: {0}")
	@ValueSource(strings = {"0", "100"})
	void createProductPrice(BigDecimal price) {
		// when
		ProductPrice result = new ProductPrice(price);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getPrice()).isEqualTo(price);
	}

	@DisplayName("상품 가격을 생성 시 상품 가격이 음수인 경우 예외가 발생한다")
	@ParameterizedTest(name = "상품 가격: {0}")
	@ValueSource(strings = {"-1", "-100"})
	void createProductPriceMinus(BigDecimal price) {
		// when then
		assertThatThrownBy(() -> new ProductPrice(price))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("상품 가격은 0원보다 작을 수 없습니다.");
	}
}
