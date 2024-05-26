package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.common.domain.NonEmptyName;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.menu.domain.MenuGroup;

class MenuGroupTest {
	private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("메뉴 그룹 이름이 null이거나 비어있으면 메뉴 그룹 객체를 생성할 수 없다.")
	void createMenuGroupWithInvalidName(String invalidName) {
		// Given
		UUID id = UUID.randomUUID();

		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new kitchenpos.menu.domain.MenuGroup(id, invalidName))
			.withMessage(NonEmptyName.NULL_OR_EMPTY_NAME_ERROR);
	}

	@Test
	@DisplayName("유효한 ID와 이름으로 메뉴 그룹 객체를 생성하면 정상적으로 생성된다.")
	void createMenuGroupWithValidArguments() {
		// Given
		UUID id = UUID.randomUUID();
		String name = "유효한 메뉴 그룹 이름";

		// When
		kitchenpos.menu.domain.MenuGroup menuGroup = new MenuGroup(id, name);

		// Then
		assertEquals(id, menuGroup.getId());
		assertEquals(name, menuGroup.getName());
	}
}