package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
	private final BigDecimal price;

	public ProductPrice(BigDecimal price) {
		verifyPrice(price);
		this.price = price;
	}

	private void verifyPrice(BigDecimal price) {
		if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("상품 가격은 0원보다 작을 수 없습니다. price: " + price);
		}
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ProductPrice that = (ProductPrice) o;
		return Objects.equals(price, that.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(price);
	}
}
