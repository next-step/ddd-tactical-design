package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupTest {

	@DisplayName("메뉴그룹을 생성한다")
	@Test
	void create() {
		// given
		UUID id = UUID.randomUUID();
		MenuGroupName name = new MenuGroupName("name");

		// when
		MenuGroup result = new MenuGroup(id, name);
		
		// then
		assertThat(result).isNotNull();
	}
}
