package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.menus.domain.tobe.domain.MenuName;

class MenuNameTest {

    @DisplayName("이름은 Null이나 빈값을 가질 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void name(String name) {
        assertThatThrownBy(() -> MenuName.of(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 필수로 입력되야 합니다.");
    }
}
