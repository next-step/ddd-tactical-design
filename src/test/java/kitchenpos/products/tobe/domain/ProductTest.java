package kitchenpos.products.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.common.infra.FakePurgomalumClient;

public class ProductTest {
	private PurgomalumClient purgomalumClient;

	@BeforeEach
	void setUp() {
		purgomalumClient = new FakePurgomalumClient();
	}

	@ParameterizedTest
	@ValueSource(longs = {14_000L, 20_000L})
	void 상품_가격을_변경할_수_있다(long price) {
		// given
		Product product = new Product(new DisplayedName("강정치킨", purgomalumClient), new Price(17_000L));

		// when
		product.changePrice(new Price(price));

		// then
		Assertions.assertThat(product.getPrice()).isEqualTo(new Price(price));
	}
}
