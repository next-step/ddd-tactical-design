package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductTest {
	@Test
	void 상품을_생성할_수_있다() {
		// given
		DisplayedName displayedName = new DisplayedName("강정치킨", name -> false);
		Price price = new Price(new BigDecimal(17_000L));

		// when
		Product product = new Product(displayedName, price);

		// & then
		assertAll(
			() -> assertThat(product).isNotNull(),
			() -> assertThat(product.getName()).isEqualTo(displayedName),
			() -> assertThat(product.getPrice()).isEqualTo(price)
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"14000", "20000"})
	void 상품_가격을_변경할_수_있다(BigDecimal value) {
		// given
		DisplayedName displayedName = new DisplayedName("강정치킨", name -> false);
		Price price = new Price(new BigDecimal(17_000L));
		Product product = new Product(displayedName, price);

		// when
		product.changePrice(new Price(value));

		// then
		Assertions.assertThat(product.getPrice()).isEqualTo(new Price(value));
	}
}
