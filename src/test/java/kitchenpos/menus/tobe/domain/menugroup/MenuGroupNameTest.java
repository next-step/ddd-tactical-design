package kitchenpos.menus.tobe.domain.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

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

    @DisplayName("MenuGroup의 Name 생성 시 값이 비어있으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createWithValidName(String name) {
        assertThatThrownBy(() -> new MenuGroupName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
