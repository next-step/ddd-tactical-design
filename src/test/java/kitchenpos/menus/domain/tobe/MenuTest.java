package kitchenpos.menus.domain.tobe;

//### 메뉴
//
//        - `MenuGroup`은 식별자와 이름을 가진다.
//        - `Menu`는 식별자와 `Displayed Name`, 가격, `MenuProducts`를 가진다.
//        - `DisplayedName`에는 `Profanity`가 포함될 수 없다.
//        - `Menu`는 특정 `MenuGroup`에 속한다.
//        - `Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.
//        - `Menu`의 가격이 `MenuProducts`의 금액의 합보다 크면 `NotDisplayedMenu`가 된다.
//        - `MenuProduct`는 가격과 수량을 가진다.

import kitchenpos.common.domain.DisplayedName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures.*;


class MenuTest {
    @Test
    @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다")
    void menuProductQuantity() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuProducts(menuProduct(-1, 3)));

        Assertions.assertDoesNotThrow(
                () -> menuProducts(menuProduct(10_000, 3)));
    }

    @Test
    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다")
    void menuPrice() {
        final DisplayedName name = new DisplayedName("치킨", new FakeProfanities());
        final MenuProducts products = menuProducts(menuProduct(10_000, 3));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> menu(name, -1, products));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> menu(name, 40_000, products));
    }

    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    void menuProductPrice() {
        final DisplayedName name = new DisplayedName("치킨", new FakeProfanities());
        final MenuProducts products = menuProducts(menuProduct(10_000, 3));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> menu(name, 40_000, products));

        Assertions.assertDoesNotThrow(
                () -> menu(name, 30_000, products));
    }


    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다")
    void createMenuSuccess() {
        final DisplayedName name = new DisplayedName("치킨", new FakeProfanities());
        final MenuProducts products = menuProducts(menuProduct(10_000, 3));

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> menu(name, 40_000, products));

        Assertions.assertDoesNotThrow(
                () -> menu(name, 30_000, products));
    }

}