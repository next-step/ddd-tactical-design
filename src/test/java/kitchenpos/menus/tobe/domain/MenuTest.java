package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import kitchenpos.menus.tobe.domain.vo.DisplayedName;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.domain.vo.Profanities;

/**
 - `MenuGroup`은 식별자와 이름을 가진다.
 - `Menu`는 식별자와 `Displayed Name`, 가격, `MenuProducts`를 가진다.
 - `Menu`는 특정 `MenuGroup`에 속한다.
 - `Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.
 - `Menu`의 가격이 `MenuProducts`의 금액의 합보다 크면 `NotDisplayedMenu`가 된다.
 - `MenuProduct`는 가격과 수량을 가진다.
 */

class MenuTest {

    @Test
    void 메뉴를_생성할수_있다() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> createMenu(16_000));
    }

    @Test
    void Menu_가격이_MenuProducts_금액_합보다_크면_NotDisplayedMenu_된다() {

        //given
        //when
        final Menu menu = createMenu(17_000);

        //then
        assertThat(menu.isDisplayed()).isFalse();

    }

    private Menu createMenu(final long price) {
        final UUID id = UUID.randomUUID();
        final Profanities profanities = new FakeProfanities();
        final DisplayedName displayedName = new DisplayedName("치킨 세트", profanities);
        final MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(price));
        final boolean displayed = true;
        final MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), "치킨 세트 메뉴 그룹");
        final MenuProducts menuProducts = new MenuProducts(createMenuProducts());

        final Menu menu = new Menu(id, displayedName, menuPrice, displayed, menuGroup, menuProducts);

        return menu;
    }

    private List<MenuProduct> createMenuProducts() {
        final UUID id = UUID.randomUUID();
        final long price = 16_000L;
        final int quantity = 1;

        final MenuProduct menuProduct = new MenuProduct(id, price, quantity);

        return Arrays.asList(menuProduct);
    }
}

