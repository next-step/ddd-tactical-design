package kitchenpos.common.domain.fixture;

import kitchenpos.common.domain.DisplayedName;

public class DisplayedNameFixture {
	public static DisplayedName 이름(String value) {
		return new DisplayedName(value, text -> false);
	}
}
