package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuNameTest {

  @DisplayName("메뉴 이름은 공백일 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void emptyNameTest(String nullOrEmpty) {
    //nullOrEmpty
    boolean isNotProfanity = false;

    //when & then
    assertThatThrownBy(() -> new MenuName(nullOrEmpty, isNotProfanity))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴 이름은 비속어가 포함될 수 없다.")
  @Test
  void profanityNameTest() {
    //nullOrEmpty
    String notNullOrEmptyName = "올바른이름";
    boolean isProfanity = true;

    //when & then
    assertThatThrownBy(() -> new MenuName(notNullOrEmptyName, isProfanity))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("같은 이름인지 비교할 수 있다.")
  @Test
  void equalsTest() {
    //given
    String given = "같은이름";
    boolean isNotProfanity = false;
    MenuName name = new MenuName(given, isNotProfanity);
    MenuName theOther = new MenuName(given, isNotProfanity);

    //when & then
    assertThat(name.equals(theOther)).isTrue();
  }

}
