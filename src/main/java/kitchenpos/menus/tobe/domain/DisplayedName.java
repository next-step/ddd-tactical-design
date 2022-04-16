package kitchenpos.menus.tobe.domain;

public class DisplayedName {
	private String name;

	public DisplayedName(String name, Properties properties) {
		if (properties.contains(name)) {
			throw new IllegalArgumentException("메뉴 이름에는 비속어를 포함할 수 없습니다. name: " + name);
		}
		this.name = name;
	}
}
