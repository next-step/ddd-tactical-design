package kitchenpos.common.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.common.Value;

@Embeddable
public class Price extends Value<Price> {
	@Column(name = "price", nullable = false)
	private BigDecimal value;

	protected Price() {

	}

	public Price(BigDecimal value) {
		if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
		}

		this.value = value;
	}

	public static Price add(Price a, Price b) {
		return new Price(a.value.add(b.value));
	}

	public static Price multiply(Price a, long b) {
		return new Price(a.value.multiply(new BigDecimal(b)));
	}

	public BigDecimal getValue() {
		return value;
	}

	public int compareTo(Price price) {
		return value.compareTo(price.value);
	}
}
