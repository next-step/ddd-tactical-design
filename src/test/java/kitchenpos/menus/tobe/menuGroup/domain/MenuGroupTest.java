package kitchenpos.menus.tobe.menuGroup.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupTest {

    @DisplayName("메뉴 그룹을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final String name = "메뉴그룹";

        // when
        final MenuGroup menuGroup = new MenuGroup(name);

        // then
        assertThat(menuGroup.getName()).isEqualTo(name);
    }
}