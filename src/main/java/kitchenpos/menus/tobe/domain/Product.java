package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class Product {
	private final UUID id;
	private final String name;
	private final Price price;

	public Product(UUID id, String name, Price price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Price getPrice() {
		return price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Product product = (Product) o;
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
