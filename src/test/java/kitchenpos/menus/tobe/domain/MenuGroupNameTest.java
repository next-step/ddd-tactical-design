package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupNameTest {

    @DisplayName("메뉴그룹 이름은 공백이 될수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void test1(String name) {
        assertThatThrownBy(
            () -> new MenuGroupName(name)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("MenuGroup명은 필수입니다");
    }

}