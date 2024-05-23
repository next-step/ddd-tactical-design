package kitchenpos.common.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Quantity {
	public static final String INVALID_VALUE_ERROR = "값은 0보다 작을 수 없습니다.";

	@Column(name = "quantity", nullable = false)
	private long value;


	protected Quantity() {
	}

	public Quantity(long value) {
		if (value < 0) {
			throw new IllegalArgumentException(INVALID_VALUE_ERROR);
		}

		this.value = value;
	}

	public long getValue() {
		return value;
	}

	public BigDecimal getBigDecimalValue() {
		return BigDecimal.valueOf(value);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Quantity quantity = (Quantity)object;
		return value == quantity.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
