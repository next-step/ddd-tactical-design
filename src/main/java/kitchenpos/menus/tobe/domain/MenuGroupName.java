package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class MenuGroupName {
	private final String name;

	public MenuGroupName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("메뉴그룹의 이름은 비어있을 수 없습니다. name: " + name);
		}
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final MenuGroupName that = (MenuGroupName) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
