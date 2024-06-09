package kitchenpos.menus.domain.menu;

import kitchenpos.menus.domain.tobe.menu.MenuName;
import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.products.infra.FakeProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MenuNameTest {
  private ProfanityValidator profanityValidator;

  @BeforeEach
  void setUp() {
    profanityValidator = new FakeProfanityValidator();
  }

  @DisplayName("상품의 이름을 생성할 수 있다.")
  @Test
  void createMenuName() {
    MenuName actual = MenuName.of("udon", profanityValidator);

    assertAll(
            () -> assertThat(actual.getName()).isEqualTo("udon")
    );
  }

  @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
  @ParameterizedTest
  @ValueSource(strings = {"비속어", "욕설"})
  void changeMenuNameWithBadWords(String name) {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> MenuName.of(name, profanityValidator))
            .withMessageContaining("메뉴의 이름에는 비속어가 포함될 수 없다.");
  }

  @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
  @ParameterizedTest
  @NullSource
  void changeMenuNameWithNull(String name) {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> MenuName.of(name, profanityValidator))
            .withMessageContaining("메뉴의 이름이 올바르지 않으면 등록할 수 없다.");
  }
}
