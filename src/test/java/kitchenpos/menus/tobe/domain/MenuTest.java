package kitchenpos.menus.tobe.domain;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.fixture.MenuFixture;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("메뉴(Menu)는")
class MenuTest {
    private final Name name = new Name("메뉴 이름", new FakeProfanities());
    private final Amount amount = new Amount(1000L);
    private final MenuGroup menuGroup = MenuFixture.메뉴그룹();
    private final MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록();

    @Test
    @DisplayName("생성할 수 있다.")
    void create() {
        final Menu menu = new Menu(name, amount, menuGroup, true, menuProducts);
        assertThat(menu.getId()).isNotNull();
        assertThat(menu).isEqualTo(new Menu(name, amount, menuGroup, true, menuProducts));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품이 0개 이하면 IllegalArgumentExcpetion이 발생한다.")
    void create(List<MenuProduct> menuProducts) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Menu(name, amount, menuGroup, menuProducts);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @ParameterizedTest
    @ValueSource(strings = "100000")
    @DisplayName("가격(Amount)이 MenuProducts의 금액의 합보다 크면 IllegalArugmentException이 발생한다.")
    void create(final long value) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Menu(name, new Amount(value), menuGroup, true, menuProducts);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

}
