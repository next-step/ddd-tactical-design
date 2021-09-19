package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.Value;

public class Quantity extends Value<Quantity> {
	private final long value;

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
