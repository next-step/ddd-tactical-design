package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import kitchenpos.menus.doubles.fakeclient.InMemoryPurgomalumClient;
import kitchenpos.menus.tobe.domain.MenuGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupTest {

    @DisplayName("메뉴 그룹의 이름에 비속어가 포함되어있다면 생성할 수 없다.")
    @Test
    void menu_group_name_contain_profanity() {
        String name = "욕설";

        assertThatThrownBy(() -> new MenuGroup(name, new InMemoryPurgomalumClient()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 이름이 비어있다면 생성할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void menu_group_name_null_or_empty(String name) {
        assertThatThrownBy(() -> new MenuGroup(name, new InMemoryPurgomalumClient()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹 생성")
    @Test
    void create_menu_group() {
        assertAll(
                () -> assertDoesNotThrow(() -> new MenuGroup("그룹 이름", new InMemoryPurgomalumClient())),
                () -> assertThat(new MenuGroup("그룹 이름", new InMemoryPurgomalumClient())).isInstanceOf(MenuGroup.class)
        );
    }
}
