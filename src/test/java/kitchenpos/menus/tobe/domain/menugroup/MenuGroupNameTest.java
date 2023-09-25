package kitchenpos.menus.tobe.domain.menugroup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {

    @DisplayName("메뉴 그룹 이름은 필수로 갖는다.")
    @ParameterizedTest
    @NullAndEmptySource
    void menuGroupName_should_exist(String name) {
        assertThatThrownBy(() -> new MenuGroupName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 그룹명이 존재하지 않습니다.");
    }
}