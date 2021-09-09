package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;

public class Product {
	private final DisplayedName name;
	private Price price;

	public Product(DisplayedName name, Price price) {
		this.name = name;
		this.price = price;
	}

	public void changePrice(Price price) {
		this.price = price;
	}

	public Price getPrice() {
		return price;
	}
}
