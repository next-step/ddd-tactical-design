package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴 그룹")
class MenuGroupTest {

    private static final String 메뉴그룹_이름 = "메뉴그룹 이름";

    @DisplayName("메뉴그룹을 생성한다.")
    @Test
    void create() {
        MenuGroupName menuGroupName = MenuGroupName.from(메뉴그룹_이름);
        MenuGroup actual = MenuGroup.from(menuGroupName);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(메뉴그룹_이름)
        );
    }
}
