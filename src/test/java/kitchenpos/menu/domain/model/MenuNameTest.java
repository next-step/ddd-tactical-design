package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuNameValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuNameTest {
    @DisplayName("MenuName을 생성할 수 있다")
    @Test
    void createMenuName() {
        // given
        final String name = "메뉴";

        // when
        final MenuName menuName = MenuName.of(name, n -> false);

        // then
        assertThat(menuName.isSameName(name)).isTrue();
    }

    @DisplayName("MenuName에 빈 값이 들어가면 예외가 발생한다")
    @Test
    void createMenuNameWithEmptyOrNull() {
        // when
        final Throwable thrown = catchThrowable(() -> MenuName.of(null, n -> false));

        // then
        assertThat(thrown).isInstanceOf(MenuNameValidationException.class)
                .hasMessage("메뉴 이름을 입력하세요");
    }

    @DisplayName("MenuName 검증이 실패하면 예외가 발생한다")
    @Test
    void createMenuNameWithInvalidValidator() {
        // given
        String menuName = "메뉴";

        // when
        final Throwable thrown = catchThrowable(() -> MenuName.of(menuName, n -> true));

        // then
        assertThat(thrown).isInstanceOf(MenuNameValidationException.class)
                .hasMessage("메뉴 이름에 비속어가 포함되어 있습니다. name: " + menuName);
    }
}