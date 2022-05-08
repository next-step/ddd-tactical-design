package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {
	@Column(name = "price", nullable = false)
	private final BigDecimal value;

	public Price(BigDecimal value) {
		this.value = validatePrice(value);
	}

	private BigDecimal validatePrice(BigDecimal price) {
		if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException();
		}

		return price;
	}

	public BigDecimal getValue() {
		return value;
	}

	public boolean isGreaterThan(BigDecimal target) {
		return value.compareTo(target) > 0;
	}
}
