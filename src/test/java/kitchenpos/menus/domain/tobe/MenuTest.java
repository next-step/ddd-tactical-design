package kitchenpos.menus.domain.tobe;

import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.support.MenuGenerator.createMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuTest {

    @DisplayName("Menu는 식별자와 Menu Name, 가격, MenuProducts를 가진다.")
    @Test
    void create() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 1l, BigDecimal.valueOf(16000)),
                createMenuProduct(2L, 1l, BigDecimal.valueOf(10000)));
        final Long givenMenuGroupId = 2L;
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(26000),
                givenMenuGroupId, true, givenMenuProducts
        );

        assertAll(
                () -> assertThat(actual).extracting("name").isNotNull(),
                () -> assertThat(actual).extracting("price").isNotNull(),
                () -> assertThat(actual).extracting("menuProducts").isNotNull()
        );
    }

    @DisplayName("`Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.")
    @Test
    void create_with_invalid_price() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 1l, BigDecimal.valueOf(16000)),
                createMenuProduct(2L, 1l, BigDecimal.valueOf(10000)));
        final Long givenMenuGroupId = 2L;
        final BigDecimal invalidPrice = BigDecimal.valueOf(30000);

        assertThatCode(() -> {
            new Menu(
                    "반반치킨", new StubBanWordFilter(false), invalidPrice,
                    givenMenuGroupId, true, givenMenuProducts
            );
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 보이게 할 수 있다.")
    @Test
    void show() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 1l, BigDecimal.valueOf(16000)),
                createMenuProduct(2L, 1l, BigDecimal.valueOf(10000)));
        final Long givenMenuGroupId = 2L;
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(26000),
                givenMenuGroupId, false, givenMenuProducts
        );

        actual.show();

        assertThat(actual.getDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 보이지 않게 할 수 있다.")
    @Test
    void hide() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 1l, BigDecimal.valueOf(16000)),
                createMenuProduct(2L, 1l, BigDecimal.valueOf(10000)));
        final Long givenMenuGroupId = 2L;
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(26000),
                givenMenuGroupId, true, givenMenuProducts
        );

        actual.hide();

        assertThat(actual.getDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void change_price() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 1l, BigDecimal.valueOf(16000)),
                createMenuProduct(2L, 1l, BigDecimal.valueOf(10000))
        );
        final Long givenMenuGroupId = 2L;
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(26000),
                givenMenuGroupId, true, givenMenuProducts
        );
        final BigDecimal newPrice = BigDecimal.valueOf(10000);

        actual.changePrice(newPrice);

        assertThat(actual.getPrice()).isEqualTo(new MenuPrice(newPrice));
    }

    @DisplayName("Menu의 가격 변경시 MenuProducts의 금액의 합보다 적거나 같아야 한다.")
    @Test
    void change_price_with_bigger_than_menu_products_sum() {
        final List<MenuProduct> givenMenuProducts = Arrays.asList(
                createMenuProduct(1L, 1l, BigDecimal.valueOf(16000)),
                createMenuProduct(2L, 1l, BigDecimal.valueOf(10000))
        );
        final Long givenMenuGroupId = 2L;
        final Menu actual = new Menu(
                "반반치킨", new StubBanWordFilter(false), BigDecimal.valueOf(26000),
                givenMenuGroupId, true, givenMenuProducts
        );
        final BigDecimal newPrice = BigDecimal.valueOf(30000);

        assertThatCode(() -> actual.changePrice(newPrice)).isInstanceOf(IllegalArgumentException.class);
    }
}
