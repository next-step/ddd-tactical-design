package kitchenpos.menus.domain.tobe;

import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.support.MenuGenerator.createMenuGroup;
import static kitchenpos.support.MenuGenerator.createMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuTest {

    @DisplayName("Menu는 식별자와 Menu Name, 가격, MenuProducts를 가진다.")
    @Test
    void create() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(createMenuProduct(1L, 2l), createMenuProduct(2L, 2l));
        final MenuGroup menuGroup = createMenuGroup("두마리메뉴");
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(16000),
                menuGroup, true, givenMenuProducts
        );

        assertAll(
                () -> assertThat(actual).extracting("name").isNotNull(),
                () -> assertThat(actual).extracting("price").isNotNull(),
                () -> assertThat(actual).extracting("menuProducts").isNotNull()
        );
    }

    @DisplayName("메뉴를 보이게 할 수 있다.")
    @Test
    void show() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(createMenuProduct(1L, 2l), createMenuProduct(2L, 2l));
        final MenuGroup menuGroup = createMenuGroup("두마리메뉴");
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(16000),
                menuGroup, false, givenMenuProducts
        );

        actual.show();

        assertThat(actual.getDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 보이지 않게 할 수 있다.")
    @Test
    void hide() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(createMenuProduct(1L, 2l), createMenuProduct(2L, 2l));
        final MenuGroup menuGroup = createMenuGroup("두마리메뉴");
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(16000),
                menuGroup, true, givenMenuProducts
        );

        actual.hide();

        assertThat(actual.getDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void change_price() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(createMenuProduct(1L, 2l), createMenuProduct(2L, 2l));
        final MenuGroup menuGroup = createMenuGroup("두마리메뉴");
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(16000),
                menuGroup, true, givenMenuProducts
        );
        final BigDecimal newPrice = BigDecimal.valueOf(10000);

        actual.changePrice(newPrice);

        assertThat(actual.getPrice()).isEqualTo(newPrice);
    }
}
