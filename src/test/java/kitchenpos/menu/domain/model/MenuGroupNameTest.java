package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuGroupNameValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupNameTest {

    @DisplayName("`MenuGroupName`을 생성할 수 있다")
    @Test
    void createMenuGroupName() {
        // given
        final String name = "메뉴 그룹";

        // when
        final MenuGroupName menuGroupName = MenuGroupName.of(name);

        // then
        assertThat(menuGroupName.isSameName(name)).isTrue();
    }

    @DisplayName("`MenuGroupName`에 빈 값이 들어가면 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void createMenuGroupNameWithEmptyOrNull(String name) {
        // when
        final Throwable thrown = catchThrowable(() -> MenuGroupName.of(name));

        // then
        assertThat(thrown).isInstanceOf(MenuGroupNameValidationException.class)
                .hasMessage("메뉴 그룹 이름을 입력하세요");
    }
}