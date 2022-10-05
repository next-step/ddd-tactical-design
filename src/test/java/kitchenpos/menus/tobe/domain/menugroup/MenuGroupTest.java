package kitchenpos.menus.tobe.domain.menugroup;

import kitchenpos.global.vo.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴 그룹")
class MenuGroupTest {

    @DisplayName("메뉴 그룹 생성")
    @Test
    void createMenuGroup() {
        MenuGroup menuGroup = new MenuGroup(new DisplayedName("두마리메뉴"));

        assertThat(menuGroup.getName()).isEqualTo(new DisplayedName("두마리메뉴"));
    }
}
