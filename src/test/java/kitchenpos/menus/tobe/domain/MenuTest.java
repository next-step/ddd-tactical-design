package kitchenpos.menus.tobe.domain;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.fixture.MenuFixture;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("메뉴(Menu)는")
class MenuTest {
    private final Name name = new Name("메뉴 이름", new FakeProfanities());
    private final Amount amount = new Amount(1000L);
    private final MenuGroup menuGroup = MenuFixture.메뉴그룹();
    private final List<MenuProduct> menuProducts = Arrays.asList(MenuFixture.메뉴상품(), MenuFixture.메뉴상품());

    @Test
    @DisplayName("생성할 수 있다.")
    void create() {
        final Menu menu = new Menu(name, amount, menuGroup, menuProducts);
        assertThat(menu.getId()).isNotNull();
        assertThat(menu).isEqualTo(new Menu(name, amount, menuGroup, menuProducts));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품이 0개 이하면 IllegalArgumentExcpetion이 발생한다.")
    void create(List<MenuProduct> menuProducts) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Menu(name, amount, menuGroup, menuProducts);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

}
