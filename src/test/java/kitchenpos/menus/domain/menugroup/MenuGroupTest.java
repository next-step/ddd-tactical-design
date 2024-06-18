package kitchenpos.menus.domain.menugroup;

import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MenuGroupTest {
  @DisplayName("메뉴 그룹을 생성할 수 있다.")
  @Test
  void createMenuGroup() {
    UUID uuid = UUID.randomUUID();
    MenuGroup actual = MenuGroup.of(uuid, "메뉴그룹");

    assertAll(
            () -> assertThat(actual.getId()).isEqualTo(uuid),
            () -> assertThat(actual.getMenuGroupName()).isEqualTo("메뉴그룹")
    );

  }
}
