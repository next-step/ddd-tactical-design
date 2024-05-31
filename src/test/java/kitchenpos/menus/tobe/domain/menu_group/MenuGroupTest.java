package kitchenpos.menus.tobe.domain.menu_group;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupTest {

  @DisplayName("메뉴 그룹을 생성할 수 있다.")
  @Test
  void create() {
    MenuGroupName menuGroupName = MenuGroupFixture.normalMenuGroupName();
    MenuGroup menuGroup = MenuGroupFixture.create(menuGroupName);
    assertThat(menuGroup.getName()).isEqualTo(menuGroupName);
  }
}
