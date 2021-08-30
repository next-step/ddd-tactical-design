package kitchenpos.products.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.Fixtures;
import kitchenpos.common.domain.Price;

public class ProductTest {

	@ParameterizedTest
	@ValueSource(longs = {14_000L, 20_000L})
	void 상품_가격을_변경할_수_있다(long price) {
		// given
		Product product = Fixtures.product("강정치킨", 17_000L);

		// when
		product.changePrice(new Price(price));

		// then
		Assertions.assertThat(product.getPrice()).isEqualTo(new Price(price));
	}
}
