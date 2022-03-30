package kitchenpos.menus.tobe.domain.model;

import kitchenpos.menus.tobe.domain.vo.MenuGroupName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupTest {

    @DisplayName("메뉴그룹(MenuGroup) 은 반드시 식별자와 이름을 가진다.")
    @Test
    void create() {
        String 메뉴_그룹_이름 = "런치 세트 메뉴";

        MenuGroup group = new MenuGroup(메뉴_그룹_이름);

        assertAll(
                () -> assertThat(group.getId()).isNotNull(),
                () -> assertThat(group.getName()).isEqualTo(new MenuGroupName(메뉴_그룹_이름))
        );
    }

}
