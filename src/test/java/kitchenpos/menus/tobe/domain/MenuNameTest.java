package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.menu.domain.MenuName;

class MenuNameTest {

	private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("메뉴 이름이 null이거나 비어있으면 메뉴 이름 객체를 생성할 수 없다.")
	void menuNameOfNullOrEmpty(String invalidName) {
		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new kitchenpos.menu.domain.MenuName(invalidName, purgomalumClient))
			.withMessage(kitchenpos.menu.domain.MenuName.NULL_OR_EMPTY_NAME_ERROR);
	}

	@ParameterizedTest
	@ValueSource(strings = {"비속어가 포함된 메뉴 이름", "욕설이 포함된 메뉴 이름"})
	@DisplayName("메뉴 이름에 비속어가 포함되면 메뉴 이름 객체를 생성할 수 없다.")
	void menuNameWithProfanity(String nameWithProfanity) {
		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new kitchenpos.menu.domain.MenuName(nameWithProfanity, purgomalumClient))
			.withMessage(kitchenpos.menu.domain.MenuName.NAME_WITH_PROFANITY_ERROR);
	}

	@Test
	@DisplayName("유효한 메뉴 이름을 입력하면 메뉴 이름 객체가 정상적으로 생성된다.")
	void menuName() {
		// Given
		String validName = "유효한 메뉴 이름";

		// When
		kitchenpos.menu.domain.MenuName menuName = new MenuName(validName, purgomalumClient);

		// Then
		assertEquals(validName, menuName.getValue());
	}
}
