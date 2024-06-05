package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuNameTest {

  @DisplayName("메뉴명을 등록할 수 있다.")
  @Test
  void create() {
    String expected = "메뉴명";
    MenuName menuName = MenuFixture.createMenuName(expected);
    assertThat(menuName.getName()).isEqualTo(expected);
  }

  @DisplayName("메뉴명은 욕설이 포함될 수 없으며 0자 이상이어야 한다.")
  @NullSource
  @ValueSource(strings = {"욕설", "비속어"})
  @ParameterizedTest
  void create(String expected) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> MenuFixture.createMenuName(expected));
  }
}
