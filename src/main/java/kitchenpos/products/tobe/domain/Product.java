package kitchenpos.products.tobe.domain;

import java.util.UUID;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;

public class Product {
	private final UUID id;
	private final DisplayedName name;
	private Price price;

	public Product(DisplayedName name, Price price) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.price = price;
	}

	public void changePrice(Price price) {
		this.price = price;
	}

	public UUID getId() {
		return id;
	}

	public DisplayedName getName() {
		return name;
	}

	public Price getPrice() {
		return price;
	}
}
