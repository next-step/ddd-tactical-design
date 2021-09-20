package kitchenpos.products.tobe.domain.fixture;

import java.math.BigDecimal;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.products.tobe.domain.Product;

public class ProductFixture {
	public static Product 상품(final String name, final long price) {
		return new Product(
			new DisplayedName(name, (text) -> false),
			new Price(new BigDecimal(price))
		);
	}
}
