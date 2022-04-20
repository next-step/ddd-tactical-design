package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import kitchenpos.menus.doubles.fakeclient.InMemoryPurgomalumClient;
import kitchenpos.menus.tobe.domain.MenuName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuNameTest {

    @DisplayName("메뉴 이름 생성")
    @Test
    void create_menu_name() {
        assertAll(
                () -> assertDoesNotThrow(() -> new MenuName(new InMemoryPurgomalumClient(), "메뉴 이름")),
                () -> assertThat(new MenuName(new InMemoryPurgomalumClient(), "메뉴 이름"))
                        .isInstanceOf(MenuName.class)
        );
    }
    @DisplayName("메뉴 이름에 비속어가 포함되어 있다면 생성할 수 없다.")
    @Test
    void menu_name_contain_profanity() {
        assertThatThrownBy(() -> new MenuName(new InMemoryPurgomalumClient(), "욕설"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
