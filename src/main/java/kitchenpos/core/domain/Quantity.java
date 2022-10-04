package kitchenpos.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.core.specification.QuantitySpecification;

@Embeddable
public class Quantity {

	@Column(name = "quantity", nullable = false)
	private long value;

	protected Quantity() {
	}

	public Quantity(long value, QuantitySpecification quantitySpecification) {
		if (quantitySpecification.isSatisfiedBy(value)) {
			this.value = value;
		}
	}

	public BigDecimal multiply(Price price) {
		return price.multiply(this);
	}

	public long getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Quantity quantity = (Quantity)o;
		return getValue() == quantity.getValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getValue());
	}

	@Override
	public String toString() {
		return "Quantity{" +
			"value=" + value +
			'}';
	}
}
