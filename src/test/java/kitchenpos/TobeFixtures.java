package kitchenpos;

import java.math.BigDecimal;

import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;

public class TobeFixtures {
	public static Product product(final String name, final long price) {
		return new Product(
			new DisplayedName(name, (text) -> false),
			new Price(new BigDecimal(price))
		);
	}
}
