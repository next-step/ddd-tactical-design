package kitchenpos.menus.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuGroupTest {

    @DisplayName("메뉴 그룹의 이름이 유효하지 않다")
    @ParameterizedTest
    @NullAndEmptySource
    void invalid_group_name(String name){
        Assertions.assertThatThrownBy(
                () -> new MenuGroup(UUID.randomUUID(), name)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"추천메뉴"})
    void create_menu_group_name(String name){
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), name);
        assertThat(menuGroup.getName()).isEqualTo(name);
    }
}
