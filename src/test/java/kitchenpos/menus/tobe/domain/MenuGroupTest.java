package kitchenpos.menus.tobe.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.core.constant.Specs;
import kitchenpos.core.domain.Name;

class MenuGroupTest {

	@DisplayName("이름에 문제 없다면 메뉴 그룹은 정상적으로 생성된다.")
	@Test
	void create() {
		// given
		Name name = new Name("아이리쉬 위스키", Specs.Product.NAME);

		// when
		assertDoesNotThrow(() -> new MenuGroup(name));
	}
}
