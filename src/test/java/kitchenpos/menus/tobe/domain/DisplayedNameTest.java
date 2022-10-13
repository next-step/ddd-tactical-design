package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
- [x] 메뉴의 이름이 올바르지 않으면 등록할 수 없다. (Null, Empty)
- [x] 메뉴의 이름에는 비속어가 포함될 수 없다.
 */
class DisplayedNameTest {
    @DisplayName("메뉴 이름은 비어있을 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void emptyName(final String name) {
        final String EMPTY_NAME_MESSAGE = "이름은 비어있을 수 없습니다.";

        assertThatThrownBy(() -> new DisplayedName(name, new FakeProfanity()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_NAME_MESSAGE);
    }

    @DisplayName("메뉴 이름에 비속어가 포함될 수 없다.")
    @Test
    void registerWithProfanity() {
        final String CONTAIN_PROFANITY_MESSAGE = "이름에 비속어가 포함될 수 없습니다.";
        final String name = "damn";

        assertThatThrownBy(() -> new DisplayedName(name, new FakeProfanity()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CONTAIN_PROFANITY_MESSAGE);
    }
}
