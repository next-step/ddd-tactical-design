package kitchenpos.menus.tobe.domain.menu_group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupNameTest {

  @DisplayName("메뉴 그룹의 이름을 등록할 수 있다.")
  @Test
  void create() {
    String expected = "메뉴그룹명";
    MenuGroupName menuGroupName = MenuGroupFixture.createMenuGroupName(expected);
    assertThat(menuGroupName.getName()).isEqualTo(expected);
  }

  @DisplayName("메뉴 그룹의 이름은 비워 둘 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void create(String expected) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> MenuGroupFixture.createMenuGroupName(expected));
  }
}
