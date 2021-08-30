package kitchenpos.fixture;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.products.tobe.domain.Product;

public class ProductFixture {
	public static Product PRODUCT(DisplayedName name, Price price) {
		return new Product(name, price);
	}
}
