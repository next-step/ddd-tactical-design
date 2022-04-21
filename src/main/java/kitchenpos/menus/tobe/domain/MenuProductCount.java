package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class MenuProductCount {
	private final int count;

	public MenuProductCount(int count) {
		if (count < 0) {
			throw new IllegalArgumentException("메뉴에 속한 상품의 수량은 0 이상이어야 합니다. count: " + count);
		}
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final MenuProductCount that = (MenuProductCount) o;
		return count == that.count;
	}

	@Override
	public int hashCode() {
		return Objects.hash(count);
	}
}
