package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuGroupNameTest {

    @DisplayName("메뉴 그룹명 생성")
    @Test
    void create() {
        MenuGroupName menuGroupName = new MenuGroupName("메뉴그룹");

        assertThat(menuGroupName).isNotNull();
    }
}




