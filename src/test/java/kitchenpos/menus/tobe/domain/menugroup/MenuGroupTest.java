package kitchenpos.menus.tobe.domain.menugroup;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupTest {

  @DisplayName("메뉴그룹의 이름이 비어있을 수 없다.")
  @ParameterizedTest
  @NullAndEmptySource
  void notAllowNullOrEmptyNameTest(String given) {
    //when & then
    assertThatThrownBy(() -> new MenuGroup(given)).isInstanceOf(IllegalArgumentException.class);
  }

}
