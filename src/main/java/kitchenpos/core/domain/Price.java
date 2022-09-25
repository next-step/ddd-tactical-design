package kitchenpos.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.ColumnDefault;

import kitchenpos.core.specification.PriceSpecification;

@Embeddable
public class Price {

	@Column(name = "price", nullable = false)
	@ColumnDefault("0")
	private BigDecimal value;

	protected Price() {
	}

	public Price(BigDecimal value, PriceSpecification priceSpecification) {
		if (isValid(value, priceSpecification)) {
			this.value = value;
		}
	}

	public void change(BigDecimal price, PriceSpecification priceSpecification) {
		if (isValid(price, priceSpecification)) {
			this.value = price;
		}
	}

	private boolean isValid(BigDecimal price, PriceSpecification priceSpecification) {
		return priceSpecification.isSatisfiedBy(price);
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
		Price other = (Price)o;
		return value.equals(other.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "Price{" +
			"price=" + value +
			'}';
	}
}
