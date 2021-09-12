package kitchenpos.menus.tobe.domain.model;

import static kitchenpos.fixture.MenuProductsFixture.CHEAP_MENU_PRODUCTS;
import static kitchenpos.fixture.MenuProductsFixture.MENU_PRODUCTS;
import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("create - 메뉴를 생성할 수 있다")
    @Test
    void menu() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        final Menu menu = new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(10000L), menuGroup, true, MENU_PRODUCTS());
        assertThat(menu).isNotNull();
    }

    @DisplayName("create - Menu의 가격이 MenuProducts의 금액의 합보다 크면 NotDisplayedMenu가 된다.")
    @Test
    void hideMenuWhenCreated() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        final Menu menu = new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(1050L), menuGroup, true, MENU_PRODUCTS());
        assertThat(menu).isNotNull();
    }

    @DisplayName("changePrice - 메뉴가격을 변경할 수 있다")
    @Test
    void changePrice() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        final Menu menu = new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(10000L), menuGroup, true, MENU_PRODUCTS());
        final Menu priceChangedMenu = menu.changePrice(new Price(20000L));
        assertThat(priceChangedMenu.getPrice()).isEqualTo(new Price(20000L));
    }

    @DisplayName("changePrice - Menu의 가격이 MenuProducts의 금액의 합보다 크면 NotDisplayedMenu가 된다.")
    @Test
    void hideMenuWhenChangingPrice() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        final Menu menu = new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(1000L), menuGroup, true, CHEAP_MENU_PRODUCTS());

        final Menu priceChangedMenu = menu.changePrice(new Price(20000L));
        assertThat(priceChangedMenu.isDisplayed()).isFalse();
    }

    @DisplayName("display - 메뉴를 노출할 수 있다")
    @Test
    void display() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        final Menu menu = new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(1000L), menuGroup, true, MENU_PRODUCTS());

        final Menu displayedMenu = menu.display();
        assertThat(displayedMenu.isDisplayed()).isTrue();
    }

    @DisplayName("display - Menu의 가격이 MenuProducts의 금액의 합보다 크면 NotDisplayedMenu가 된다.")
    @Test
    void hideMenuWhenDisplay() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        final Menu menu = new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(10000L), menuGroup, false, CHEAP_MENU_PRODUCTS());

        final Menu displayedMenu = menu.display();
        assertThat(displayedMenu.isDisplayed()).isFalse();
    }

    @DisplayName("hide - Menu를 숨길 수 있다")
    @Test
    void hideMenu() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("메뉴그룹1", purgomalumClient));
        final Menu menu = new Menu(new DisplayedName("메뉴1", purgomalumClient), new Price(10000L), menuGroup, true, MENU_PRODUCTS());

        final Menu hidedMenu = menu.hide();
        assertThat(hidedMenu.isDisplayed()).isFalse();
    }

}
