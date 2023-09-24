package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.application.FakeMenuNameProfanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class NameTest {


    private FakeMenuNameProfanities menuNameProfanities;
    private MenuNamePolicy menuNamePolicy;

    @BeforeEach
    void setUp() {
        menuNameProfanities = new FakeMenuNameProfanities();
        menuNamePolicy = new MenuNamePolicy(menuNameProfanities);
    }

    @DisplayName("Menu의 Name 생성할 수 있다.")
    @Test
    void create() {
        final String name = "후라이드";
        final Name menuName = Name.from(name, menuNamePolicy);
        assertAll(
                () -> assertThat(menuName).isNotNull(),
                () -> assertThat(menuName.getValue()).isEqualTo(name)
        );
    }

    @DisplayName("Menu의 Name은 null일때 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void createWithNullName(String name) {
        assertThatThrownBy(() -> Name.from(name, menuNamePolicy))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Menu의 Name에 Profanity를 포함되면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어포함이름", "중간에욕설포함"})
    void createWithProfanityName(String name) {
        assertThatThrownBy(() -> Name.from(name, menuNamePolicy))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
