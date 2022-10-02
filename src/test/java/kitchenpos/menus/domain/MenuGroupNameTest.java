package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.menus.exception.InvalidMenuGroupNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuGroupNameTest {

    @DisplayName("메뉴그룹 이름은 Null 일 수 없다.")
    @Test
    void nullException() {
        assertThatThrownBy(() -> new MenuGroupName(null))
            .isExactlyInstanceOf(InvalidMenuGroupNameException.class);
    }

    @DisplayName("메뉴그룹의 이름은 공백으로 이루어질 수 없다.")
    @ValueSource(strings = {" ", "  ", "           "})
    @ParameterizedTest
    void blankException(String blank) {
        assertThatThrownBy(() -> new MenuGroupName(blank))
            .isExactlyInstanceOf(InvalidMenuGroupNameException.class);
    }
}
