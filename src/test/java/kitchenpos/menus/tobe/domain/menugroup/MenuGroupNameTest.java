package kitchenpos.menus.tobe.domain.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupNameTest {

    @DisplayName("메뉴 그룹 이름을 생성할 수 있다.")
    @Test
    void create() {
        final String name = "후라이드";
        final MenuGroupName menuGroupName = new MenuGroupName(name);
        assertAll(
                () -> assertNotNull(menuGroupName),
                () -> assertEquals(menuGroupName.getValue(), name)
        );
    }

    @DisplayName("메뉴 그룹 이름 생성 시 값이 null이면 예외가 발생한다.")
    @Test
    void createWithNullName() {
        assertThatThrownBy(() -> new MenuGroupName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹 이름 생성 시 값이 빈 문자열이면 예외가 발생한다.")
    @Test
    void createWithEmptyName() {
        assertThatThrownBy(() -> new MenuGroupName(""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
