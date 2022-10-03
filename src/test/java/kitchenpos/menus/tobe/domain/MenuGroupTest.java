package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class MenuGroupTest {

    @Test
    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    void createMenuGroup() {
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), "점심특선");
        Assertions.assertThat(menuGroup.getName()).isEqualTo("점심특선");
    }
}