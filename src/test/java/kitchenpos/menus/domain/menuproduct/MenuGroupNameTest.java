package kitchenpos.menus.domain.menuproduct;

import kitchenpos.menus.domain.tobe.menugroup.MenuGroupName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MenuGroupNameTest {
  @DisplayName("메뉴 그룹 이름을 생성할 수 있다.")
  @Test
  void createMenuGroupName() {
    MenuGroupName actual = MenuGroupName.of("udon");

    assertAll(
            () -> assertThat(actual.getName()).isEqualTo("udon")
    );
  }

  @DisplayName("메뉴 그룹의 이름은 비워 둘 수 없다.")
  @ParameterizedTest
  @NullAndEmptySource
  void changeMenuGroupNameWithNull(String name) {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> MenuGroupName.of(name));
  }
}
