package kitchenpos.menus.tobe.domain.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupDisplayedNameTest {

    @DisplayName("메뉴 그룹 이름을 생성할 수 있다.")
    @Test
    void create() {
        final String name = "후라이드";
        final MenuGroupDisplayedName menuGroupDisplayedName = new MenuGroupDisplayedName(name);
        assertAll(
                () -> assertNotNull(menuGroupDisplayedName),
                () -> assertEquals(menuGroupDisplayedName.getValue(), name)
        );
    }

    @DisplayName("메뉴 그룹 이름 생성 시 값이 null이면 예외가 발생한다.")
    @Test
    void createWithNullName() {
        assertThatThrownBy(() -> new MenuGroupDisplayedName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹 이름 생성 시 값이 빈 문자열이면 예외가 발생한다.")
    @Test
    void createWithEmptyName() {
        assertThatThrownBy(() -> new MenuGroupDisplayedName(""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
