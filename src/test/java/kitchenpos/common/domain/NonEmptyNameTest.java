package kitchenpos.common.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NonEmptyNameTest {
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("메뉴 그룹 이름이 null이거나 비어있으면 메뉴 그룹 이름 객체를 생성할 수 없다.")
	void menuGroupNameOfNullOrEmpty(String invalidName) {
		// When & Then
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> new NonEmptyName(invalidName))
			.withMessage(NonEmptyName.NULL_OR_EMPTY_NAME_ERROR);
	}

	@Test
	@DisplayName("유효한 메뉴 그룹 이름을 입력하면 메뉴 그룹 이름 객체가 정상적으로 생성된다.")
	void menuGroupName() {
		// Given
		String validName = "유효한 메뉴 그룹 이름";

		// When
		NonEmptyName nonEmptyName = new NonEmptyName(validName);

		// Then
		assertEquals(validName, nonEmptyName.getValue());
	}
}