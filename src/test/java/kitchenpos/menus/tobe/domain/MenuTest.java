package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
- [x] 메뉴의 가격을 변경할 수 있다.
- [x] 메뉴의 가격이 올바르지 않으면 변경할 수 없다.
- [x] 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
- [x] 메뉴는 특정 메뉴 그룹에 속해야 한다.
- [x] 메뉴의 전시상태를 변경할 수 있다. (노출, 숨김)
- [x] 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.
 */
class MenuTest {
    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void construct() {
        final Price price = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new Product(BigDecimal.valueOf(6L)));
        final Menu menu = new Menu(price, name, menuProduct, new MenuGroup());

        menu.changePrice(new Price(BigDecimal.ONE));

        assertThat(menu.getPrice()).isEqualTo(new Price(BigDecimal.ONE));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @ParameterizedTest
    @ValueSource(longs = {5L, 6L})
    void greaterThanAndEqualsMenuProductPriceSum(final long productPrice) {
        final Price menuPrice = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new Product(BigDecimal.valueOf(productPrice)));

        assertThatCode(() -> new Menu(menuPrice, name, menuProduct, new MenuGroup()))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @Test
    void consistOfMenuGroup() {
        final Price menuPrice = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new Product(BigDecimal.valueOf(6L)));
        final MenuGroup menuGroup = new MenuGroup();

        assertThatCode(() -> new Menu(menuPrice, name, menuProduct, menuGroup))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴의 전시상태를 변경할 수 있다.")
    @Test
    void displayed() {
        final Price menuPrice = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new Product(BigDecimal.valueOf(6L)));
        final MenuGroup menuGroup = new MenuGroup();

        final Menu menu = new Menu(menuPrice, name, menuProduct, menuGroup);
        menu.displayOff();
        assertThat(menu.isDisplayed()).isFalse();

        menu.displayOn();
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayOffWithInvalidPrice() {
        String message = "메뉴의 가격은 상품 가격의 합보다 작다면 전시할 수 없습니다.";

        final Price menuPrice = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new Product(BigDecimal.valueOf(4L)));
        final MenuGroup menuGroup = new MenuGroup();

        final Menu menu = new Menu(menuPrice, name, menuProduct, menuGroup);
        assertThat(menu.isDisplayed()).isFalse();

        assertThatThrownBy(() -> menu.displayOn())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        menu.changePrice(new Price(BigDecimal.valueOf(8L)));
        menu.displayOn();
        assertThat(menu.isDisplayed()).isTrue();

        menu.changePrice(new Price(BigDecimal.TEN));
        assertThat(menu.isDisplayed()).isFalse();
    }
}
