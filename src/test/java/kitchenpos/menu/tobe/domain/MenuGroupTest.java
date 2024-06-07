package kitchenpos.menu.tobe.domain;

import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

class MenuGroupTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴 그룹명이 null 또는 빈 문자열이라면 예외가 발생한다.")
    void test1(String name) {
        UUID id = UUID.randomUUID();

        Assertions.assertThatThrownBy(() -> new MenuGroup(id, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 그룹을 정상적으로 생성할 수 있다.")
    void test2() {
        UUID id = UUID.randomUUID();
        String name = "그룹명";

        MenuGroup menuGroup = new MenuGroup(id, name);

        Assertions.assertThat(menuGroup).isNotNull();
        Assertions.assertThat(menuGroup.getId()).isEqualTo(id);
        Assertions.assertThat(menuGroup.getName()).isEqualTo(name);
    }

}