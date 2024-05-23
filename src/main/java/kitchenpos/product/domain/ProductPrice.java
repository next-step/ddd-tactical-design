package kitchenpos.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductPrice {
	public static final String INVALID_PRICE_ERROR = "상품 가격은 0원 이상이어야 합니다.";

	@Column(name = "price", nullable = false)
	private BigDecimal value;

	protected ProductPrice() {
	}

	public ProductPrice(long value) {
		this(BigDecimal.valueOf(value));
	}

	public ProductPrice(BigDecimal value) {
		if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException(INVALID_PRICE_ERROR);
		}

		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		ProductPrice that = (ProductPrice)object;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
