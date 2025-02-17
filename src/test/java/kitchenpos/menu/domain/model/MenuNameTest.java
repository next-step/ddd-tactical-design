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
        final MenuName menuName = MenuName.of(name);

        // then
        assertThat(menuName.isSameName(name)).isTrue();
    }

    @DisplayName("MenuName에 빈 값이 들어가면 예외가 발생한다")
    @Test
    void createMenuNameWithEmptyOrNull() {
        // when
        final Throwable thrown = catchThrowable(() -> MenuName.of(null));

        // then
        assertThat(thrown).isInstanceOf(MenuNameValidationException.class)
                .hasMessage("메뉴 이름을 입력하세요");
    }
}