package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class DisplayedName {
	private String name;

	public DisplayedName(String name, Profanities profanities) {
		if (profanities.contains(name)) {
			throw new IllegalArgumentException("메뉴 이름에는 비속어를 포함할 수 없습니다. name: " + name);
		}
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final DisplayedName that = (DisplayedName) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
