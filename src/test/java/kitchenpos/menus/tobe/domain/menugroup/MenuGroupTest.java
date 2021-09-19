package kitchenpos.menus.tobe.domain.menugroup;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MenuGroupTest {

	@Test
	void 메뉴그룹을_생성할_수_있다() {
		// given
		Name name = new Name("추천메뉴");

		// when
		MenuGroup menuGroup = new MenuGroup(name);

		// & then
		assertAll(
			() -> assertThat(menuGroup).isNotNull(),
			() -> assertThat(menuGroup.getId()).isNotNull(),
			() -> assertThat(menuGroup.getName()).isEqualTo(name)
		);
	}
}