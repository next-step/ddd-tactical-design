package kitchenpos.menus.tobe.domain;

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
}
