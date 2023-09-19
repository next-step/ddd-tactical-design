package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.application.FakeMenuDisplayedNameProfanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuDisplayedNameTest {


    private FakeMenuDisplayedNameProfanities menuDisplayedNameProfanities;
    private MenuDisplayedNamePolicy menuDisplayedNamePolicy;

    @BeforeEach
    void setUp() {
        menuDisplayedNameProfanities = new FakeMenuDisplayedNameProfanities();
        menuDisplayedNamePolicy = new MenuDisplayedNamePolicy(menuDisplayedNameProfanities);
    }

    @DisplayName("메뉴 이름 생성")
    @Test
    void create() {
        final String name = "후라이드";
        final MenuDisplayedName menuDisplayedName = MenuDisplayedName.from(name, menuDisplayedNamePolicy);
        assertAll(
                () -> assertThat(menuDisplayedName).isNotNull(),
                () -> assertThat(menuDisplayedName.getValue()).isEqualTo(name)
        );
    }

    @DisplayName("메뉴 이름이 null이면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void createWithNullName(String name) {
        assertThatThrownBy(() -> MenuDisplayedName.from(name, menuDisplayedNamePolicy))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 이름에 비속어를 포함하면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어포함이름", "중간에욕설포함"})
    void createWithProfanityName(String name) {
        assertThatThrownBy(() -> MenuDisplayedName.from(name, menuDisplayedNamePolicy))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
