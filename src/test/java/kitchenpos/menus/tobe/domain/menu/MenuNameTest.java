package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.exception.MenuNameNullPointerException;
import kitchenpos.menus.exception.MenuNameProfanityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 이름")
class MenuNameTest {
    private ProfanityChecker profanityChecker;

    private static final String 메뉴_이름 = "메뉴 이름";

    @BeforeEach
    void setUp() {
        profanityChecker = new FakeProfanityChecker();
    }

    @DisplayName("[성공] 메뉴 이름을 생성한다.")
    @Test
    void success() {
        MenuName actual = MenuName.from(메뉴_이름, profanityChecker);

        assertThat(actual).isEqualTo(MenuName.from(메뉴_이름, profanityChecker));
    }

    @DisplayName("[실패] 메뉴 이름은 비워둘 수 없다.")
    @NullSource
    @ParameterizedTest
    void fail1(String name) {
        assertThatThrownBy(() -> MenuName.from(name, profanityChecker))
                .isInstanceOf(MenuNameNullPointerException.class);
    }

    @DisplayName("[실패] 메뉴 이름은 비속어를 포함할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void fail2(String name) {
        assertThatThrownBy(() -> MenuName.from(name, profanityChecker))
                .isInstanceOf(MenuNameProfanityException.class);
    }
}
