package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kitchenpos.common.Value;

@Embeddable
public class Quantity extends Value<Quantity> {
	@Column(name = "quantity", nullable = false)
	private long value;

	protected Quantity() {

	}

	public Quantity(long value) {
		if (value < 0) {
			throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
		}

		this.value = value;
	}

	public long getValue() {
		return value;
	}
}
