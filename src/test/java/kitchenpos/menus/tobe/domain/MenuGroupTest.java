package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupTest {

    private final MenuProfanityClient profanityClient = new FakeMenuProfanityClient();

    @Test
    @DisplayName("이름을 입력하여 메뉴 그룹을 만들 수 있다.")
    void menu_group() {
        MenuGroup menuGroup = new MenuGroup("메뉴 그룹 이름", profanityClient);
        MenuGroupDisplayedName menuDisplayedName = new MenuGroupDisplayedName("메뉴 그룹 이름", profanityClient);

        assertThat(menuGroup.getId()).isNotNull();
        assertThat(menuGroup.getName()).isEqualTo(menuDisplayedName);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 비어있으면 메뉴 그룹을 만들 수 없다.")
    void menu_group_empty_name(String name) {
        assertThatThrownBy(() -> new MenuGroup(name, profanityClient))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 메뉴 그룹을 만들 수 없다.")
    void menu_group_name_profanity(String name) {
        assertThatThrownBy(() -> new MenuGroup(name, profanityClient))
                .isInstanceOf(IllegalArgumentException.class);
    }
}