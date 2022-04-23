package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {
	private final UUID id;
	private final MenuGroupName menuGroupName;

	public MenuGroup(UUID id, MenuGroupName menuGroupName) {
		this.id = id;
		this.menuGroupName = menuGroupName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final MenuGroup menuGroup = (MenuGroup) o;
		return Objects.equals(id, menuGroup.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
