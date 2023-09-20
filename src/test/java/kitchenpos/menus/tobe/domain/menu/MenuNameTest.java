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

class MenuNameTest {


    private FakeMenuNameProfanities menuNameProfanities;
    private MenuNamePolicy menuNamePolicy;

    @BeforeEach
    void setUp() {
        menuNameProfanities = new FakeMenuNameProfanities();
        menuNamePolicy = new MenuNamePolicy(menuNameProfanities);
    }

    @DisplayName("메뉴 이름 생성")
    @Test
    void create() {
        final String name = "후라이드";
        final MenuName menuName = MenuName.from(name, menuNamePolicy);
        assertAll(
                () -> assertThat(menuName).isNotNull(),
                () -> assertThat(menuName.getValue()).isEqualTo(name)
        );
    }

    @DisplayName("메뉴 이름이 null이면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void createWithNullName(String name) {
        assertThatThrownBy(() -> MenuName.from(name, menuNamePolicy))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 이름에 비속어를 포함하면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어포함이름", "중간에욕설포함"})
    void createWithProfanityName(String name) {
        assertThatThrownBy(() -> MenuName.from(name, menuNamePolicy))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
