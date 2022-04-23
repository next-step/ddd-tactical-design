package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

	private final BigDecimal price;

	public static final Price ZERO = new Price(BigDecimal.ZERO);

	public Price(BigDecimal price) {
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("가격은 0원 이상이어야 합니다. price: " + price);
		}
		this.price = price;
	}

	public Price add(Price other) {
		return new Price(this.price.add(other.price));
	}

	public Price multiply(int count) {
		return new Price(this.price.multiply(BigDecimal.valueOf(count)));
	}

	public boolean isHigherThan(Price other) {
		return this.price.compareTo(other.price) > 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Price price1 = (Price) o;
		return Objects.equals(price, price1.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(price);
	}
}
