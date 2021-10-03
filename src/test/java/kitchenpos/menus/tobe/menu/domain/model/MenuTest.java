package kitchenpos.menus.tobe.menu.domain.model;

import static kitchenpos.fixture.MenuFixture.MENU1;
import static kitchenpos.fixture.MenuFixture.MENU_WITH_PRICE;
import static kitchenpos.fixture.MenuFixture.NOT_DISPLAYED_MENU;
import static kitchenpos.fixture.MenuFixture.NOT_DISPLAYED_MENU_WITH_PRICE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    private static final long EXPENSIVE_PRICE = 100000000L;

    @DisplayName("create - 메뉴를 생성할 수 있다")
    @Test
    void menu() {
        final Menu menu = MENU1();
        assertThat(menu).isNotNull();
    }

    @DisplayName("create - Menu의 가격이 MenuProducts의 금액의 합보다 크면 NotDisplayedMenu가 된다.")
    @Test
    void hideMenuWhenCreated() {
        final Menu menu = MENU_WITH_PRICE(EXPENSIVE_PRICE);
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("changePrice - 메뉴가격을 변경할 수 있다")
    @Test
    void changePrice() {
        final Menu menu = MENU1();
        final Menu priceChangedMenu = menu.changePrice(new MenuPrice(20000L));
        assertThat(priceChangedMenu.getMenuPrice()).isEqualTo(new MenuPrice(20000L));
    }

    @DisplayName("changePrice - Menu의 가격이 MenuProducts의 금액의 합보다 크면 NotDisplayedMenu가 된다.")
    @Test
    void hideMenuWhenChangingPrice() {
        final Menu menu = MENU1();

        final Menu priceChangedMenu = menu.changePrice(new MenuPrice(EXPENSIVE_PRICE));
        assertThat(priceChangedMenu.isDisplayed()).isFalse();
    }

    @DisplayName("display - 메뉴를 노출할 수 있다")
    @Test
    void display() {
        final Menu menu = NOT_DISPLAYED_MENU();

        final Menu displayedMenu = menu.display();
        assertThat(displayedMenu.isDisplayed()).isTrue();
    }

    @DisplayName("display - Menu의 가격이 MenuProducts의 금액의 합보다 크면 NotDisplayedMenu가 된다.")
    @Test
    void hideMenuWhenDisplay() {
        final Menu menu = NOT_DISPLAYED_MENU_WITH_PRICE(EXPENSIVE_PRICE);

        final Menu displayedMenu = menu.display();
        assertThat(displayedMenu.isDisplayed()).isFalse();
    }

    @DisplayName("hide - Menu를 숨길 수 있다")
    @Test
    void hideMenu() {
        final Menu menu = MENU1();

        final Menu hidedMenu = menu.hide();
        assertThat(hidedMenu.isDisplayed()).isFalse();
    }

}
