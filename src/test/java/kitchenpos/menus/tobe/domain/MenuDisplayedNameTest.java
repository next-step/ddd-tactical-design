package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuDisplayedNameTest {

    private final MenuProfanityClient profanityClient = new FakeMenuProfanityClient();

    @Test
    @DisplayName("이름을 지을수 있다.")
    void name() {
        MenuDisplayedName menuDisplayedName1 = new MenuDisplayedName("상점 제목", profanityClient);
        MenuDisplayedName menuDisplayedName2 = new MenuDisplayedName("상점 제목", profanityClient);

        assertThat(menuDisplayedName1).isEqualTo(menuDisplayedName2);
    }

    @Test
    @DisplayName("이름은 비어있을 수 없다.")
    void name_empty() {
        assertThatThrownBy(() -> new MenuDisplayedName("", profanityClient))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @DisplayName("이름이 비속어가 포함될 수 없다.")
    void name_profanity(String name) {
        assertThatThrownBy(() -> new MenuDisplayedName(name, profanityClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

}