package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {

	@DisplayName("MenuGroupName 을 생성한다")
	@Test
	void create() {
		// when
		MenuGroupName result = new MenuGroupName("이름");

		// then
		assertThat(result).isNotNull();
	}

	@DisplayName("MenuGroupName 생성 시 이름이 비어있는 경우 예외가 발생한다")
	@NullAndEmptySource
	@ParameterizedTest
	void emptyName(String name) {
		// when then
		assertThatThrownBy(() -> new MenuGroupName(name))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("메뉴그룹의 이름은 비어있을 수 없습니다.");
	}
}
