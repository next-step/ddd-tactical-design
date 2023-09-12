package kitchenpos.menus.tobe.domain;

import kitchenpos.common.FakeProfanityPolicy;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.exception.MenuDisplayedNameException;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.tobe.domain.menu.MenuDisplayedName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 이름")
class MenuDisplayedNameTest {

    private ProfanityPolicy profanityPolicy;

    @BeforeEach
    void setUp() {
        profanityPolicy = new FakeProfanityPolicy();
    }

    @DisplayName("[성공] 이름을 생성한다.")
    @Test
    void create1() {
        assertThatNoException().isThrownBy(
                () -> new MenuDisplayedName("표준어", profanityPolicy));
    }

    @DisplayName("[실패] 이름에 비속어가 포함될 수 없다.")
    @Test
    void create2() {
        assertThatThrownBy(
                () -> new MenuDisplayedName("비속어", profanityPolicy))
                .isInstanceOf(MenuDisplayedNameException.class)
                .hasMessage(MenuErrorCode.NAME_HAS_PROFANITY.getMessage());
    }

    @DisplayName("[실패] 이름은 null이거나 공백일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create_test_3(String name) {
        assertThatThrownBy(
                () -> new MenuDisplayedName(name, profanityPolicy))
                .isInstanceOf(MenuDisplayedNameException.class)
                .hasMessage(MenuErrorCode.NAME_IS_NULL_OR_EMPTY.getMessage());
    }
}
