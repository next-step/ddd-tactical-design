package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.common.Value;

@Embeddable
public class Price extends Value<Price> {
	@Column(name = "price")
	private BigDecimal value;

	protected Price() {

	}

	public Price(BigDecimal value) {
		if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
		}

		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}
}
