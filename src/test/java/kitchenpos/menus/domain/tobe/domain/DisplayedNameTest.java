package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

    @DisplayName("메뉴 이름을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new DisplayedName("탕수육 세트"))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴 이름은 비어있지 않아야 한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createInvalidName(String name) {
        assertThatThrownBy(() -> new DisplayedName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 이름은 빈 값이 아니어야 합니다. 입력 값 : " + name);
    }
}
