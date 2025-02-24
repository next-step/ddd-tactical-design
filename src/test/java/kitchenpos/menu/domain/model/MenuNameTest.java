package kitchenpos.menu.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴 이름이 비어있거나 null인 경우 예외를 던진다. ")
    void create_menu_name_exception(String value) {
        // when // then
        assertThatThrownBy(() -> new MenuName(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 이름을 채워주세요!");
    }
}
