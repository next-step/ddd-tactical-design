package kitchenpos.menu.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuPrice {
	public static final String INVALID_PRICE_ERROR = "메뉴 가격은 0원 이상이어야 합니다.";

	@Column(name = "price", nullable = false)
	private BigDecimal value;

	protected MenuPrice() {
	}

	private MenuPrice(long value) {
		this(BigDecimal.valueOf(value));
	}

	private MenuPrice(BigDecimal value) {
		if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException(INVALID_PRICE_ERROR);
		}

		this.value = value;
	}

	public static MenuPrice of(BigDecimal value) {
		return new MenuPrice(value);
	}

	public static MenuPrice of(long value) {
		return new MenuPrice(value);
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
		MenuPrice menuPrice = (MenuPrice)object;
		return Objects.equals(value, menuPrice.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
