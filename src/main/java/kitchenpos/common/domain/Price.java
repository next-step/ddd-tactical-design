package kitchenpos.common.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
	private final BigDecimal value;

	public Price(Long value) {
		if (value == null || !(value >= 0)) {
			throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
		}

		this.value = new BigDecimal(value);
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Price price = (Price)o;
		return Objects.equals(value, price.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
