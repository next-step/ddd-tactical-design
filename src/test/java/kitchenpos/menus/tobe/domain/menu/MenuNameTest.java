package kitchenpos.menus.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 이름")
class MenuNameTest {

    @DisplayName("메뉴 이름은 필수이다")
    @ParameterizedTest
    @NullAndEmptySource
    void createEmptyMenuName(final String value) {
        boolean isProfanity = true;
        assertThatThrownBy(() -> new MenuName(value, isProfanity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴명은 null 이나 공백일 수 없습니다.");
    }

    @DisplayName("메뉴 이름에 비속어를 포함할 수 없다")
    @Test
    void createProfanityMenuName() {
        assertThatThrownBy(() -> new MenuName("후라이드 치킨", true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴명에 비속어를 포함할 수 없습니다.");
    }

}
