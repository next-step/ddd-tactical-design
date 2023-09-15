package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupTest {

    @DisplayName("메뉴그룹을 생성할수 있다")
    @Test
    void test1() {
        //given
        String name = "name";

        //when
        MenuGroup result = new MenuGroup(name);

        //then
        assertAll(
            () -> assertThat(result.getId()).isNotNull(),
            () -> assertThat(result.getName()).isEqualTo(name)
        );
    }

    @DisplayName("메뉴그룹명이 공백 혹은 비어있을수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void test2(String name) {
        assertThatThrownBy(
            () -> new MenuGroup(name)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("MenuGroup명은 필수입니다");
    }
}
