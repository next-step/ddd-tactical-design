package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

	@DisplayName("메뉴이름을 생성할 수 있다")
	@Test
	void create() {
		// given
		String name = "이름";
		Properties properties = new FakeProperties();

		// when
		DisplayedName displayedName = new DisplayedName(name, properties);

		// then
		Assertions.assertThat(displayedName).isNotNull();
	}

	@DisplayName("메뉴이름 생성 시 비속어가 포함된 경우 예외가 발생한다")
	@Test
	void properties() {
		// given
		String name = FakeProperties.PROPERTY;
		Properties properties = new FakeProperties();

		// when then
		assertThatThrownBy(() -> new DisplayedName(name, properties))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("메뉴 이름에는 비속어를 포함할 수 없습니다");
	}
}
