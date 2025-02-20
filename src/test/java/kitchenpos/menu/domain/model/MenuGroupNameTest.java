package kitchenpos.menu.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴 카테고리 이름은 비어있거나 null인 경우 예외를 던진다.")
    void create_name_exception(String value) {
        // when // then
        assertThatThrownBy(() -> new MenuGroupName(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 카테고리 이름을 채워주세요!");
    }
}
