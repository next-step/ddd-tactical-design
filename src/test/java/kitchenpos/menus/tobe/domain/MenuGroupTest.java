package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupNameException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuGroupTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void 메뉴_그룹명이_존재해야_한다(String invalidMenuGroupName) {
        // given & when & then
        assertThatThrownBy(() -> new MenuGroup(invalidMenuGroupName))
                .isInstanceOf(InvalidMenuGroupNameException.class)
                .hasMessage("메뉴 그룹명이 존재해야 합니다.")
        ;
    }
}
