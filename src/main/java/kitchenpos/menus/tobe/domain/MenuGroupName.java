package kitchenpos.menus.tobe.domain;

public class MenuGroupName {
	private final String name;

	public MenuGroupName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("메뉴그룹의 이름은 비어있을 수 없습니다. name: " + name);
		}
		this.name = name;
	}
}
