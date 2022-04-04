package kitchenpos.menus.tobe.menu.domain;

import kitchenpos.MenuFixture;
import kitchenpos.menus.tobe.menu.domain.product.Price;
import kitchenpos.menus.tobe.menugroup.domain.MenuGroup;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("메뉴(Menu)는")
class MenuTest {
    private final Name name = new Name("메뉴 이름", new FakeProfanities());
    private final Price price = new Price(1000L);
    private final MenuGroup menuGroup = MenuFixture.메뉴그룹();
    private final MenuProducts menuProducts = MenuFixture.금액이불러와진_메뉴상품목록();

    @Test
    @DisplayName("생성할 수 있다.")
    void create() {
        final Menu menu = new Menu(name, price, menuGroup, true, menuProducts);
        assertThat(menu.getId()).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품이 0개 이하면 IllegalArgumentExcpetion이 발생한다.")
    void create(List<MenuProduct> menuProducts) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Menu(name, price, menuGroup, menuProducts);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @ParameterizedTest
    @ValueSource(strings = "100000")
    @DisplayName("가격(price)이 MenuProducts의 금액의 합(amount)보다 크면 IllegalArugmentException이 발생한다.")
    void create(final long value) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Menu(name, new Price(value), menuGroup, true, menuProducts);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }

    @Test
    @DisplayName("가격을 변경할 수 있다.")
    void changePrice() {
        final Menu menu = new Menu(name, price, menuGroup, true, menuProducts);
        final Price newPrice = new Price(100L);

        menu.changePrice(newPrice);

        assertThat(menu.isDisplayed()).isTrue();
        assertThat(menu.getPrice()).isEqualTo(new Menu(name, newPrice, menuGroup, true, menuProducts).getPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = "100000")
    @DisplayName("변경하려는 가격(price)이 MenuProducts의 금액의 합(amount)보다 크면 메뉴가 숨겨진다.")
    void changePrice(final BigDecimal value) {
        final Menu menu = new Menu(name, price, menuGroup, true, menuProducts);
        final Price newPrice = new Price(value);

        menu.changePrice(newPrice);

        assertThat(menu.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("숨길 수 있다.")
    void hide() {
        final Menu menu = new Menu(name, price, menuGroup, true, menuProducts);
        assertThat(menu.isDisplayed()).isTrue();

        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("노출할 수 있다.")
    void display() {
        final Menu menu = new Menu(name, price, menuGroup, false, menuProducts);
        assertThat(menu.isDisplayed()).isFalse();

        menu.display();
        assertThat(menu.isDisplayed()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = "100000")
    @DisplayName("노출할 때 가격(price)이 MenuProducts의 금액의 합(amount)보다 크면 IllegalStateException이 발생한다.")
    void display(final BigDecimal value) {
        final Menu menu = new Menu(name, price, menuGroup, true, menuProducts);
        final Price newPrice = new Price(value);
        menu.changePrice(newPrice);

        ThrowableAssert.ThrowingCallable throwingCallable = () -> menu.display();

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(throwingCallable);
    }
}
